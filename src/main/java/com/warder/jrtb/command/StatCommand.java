package com.warder.jrtb.command;

import com.warder.jrtb.service.SendBotMessageService;
import com.warder.jrtb.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StatCommand implements Command {
    private final TelegramUserService telegramUserService;
    private final SendBotMessageService botMessageService;

    public final static String STAT_TEXT = "Бот используется %s человек";

    @Autowired
    public StatCommand(SendBotMessageService botMessageService, TelegramUserService telegramUserService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        int activeUserCount = telegramUserService.retrieveAllActiveUsers().size();
        botMessageService.sendMessage(chatId, String.format(STAT_TEXT, activeUserCount));
    }
}
