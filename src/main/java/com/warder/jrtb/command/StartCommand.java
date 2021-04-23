package com.warder.jrtb.command;

import com.warder.jrtb.repository.users.TelegramUser;
import com.warder.jrtb.service.SendBotMessageService;
import com.warder.jrtb.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {

    private final SendBotMessageService messageService;
    private final TelegramUserService telegramUserService;

    @Autowired
    public StartCommand(SendBotMessageService messageService, TelegramUserService telegramUserService) {
        this.messageService = messageService;
        this.telegramUserService = telegramUserService;
    }

    public final static String START_MESSAGE =
            "Привет! Я телеграмм-бот для javarush.ru. Я помогу тебе следить за статьями тех авторов," +
                    "которые тебе интересны!";

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        telegramUserService.findByChatId(chatId).ifPresentOrElse(
                user -> {
                    user.setActive(true);
                    telegramUserService.save(user);
                },
                () -> {
                    TelegramUser telegramUser = new TelegramUser();
                    telegramUser.setActive(true);
                    telegramUser.setChat_id(chatId);
                    telegramUserService.save(telegramUser);
                }
        );

        messageService.sendMessage(chatId, START_MESSAGE);
    }
}
