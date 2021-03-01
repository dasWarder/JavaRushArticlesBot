package com.warder.jrtb.command;

import com.warder.jrtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {

    private final SendBotMessageService messageService;

    public final static String START_MESSAGE =
            "Привет! Я телеграмм-бот для javarush.ru. Я помогу тебе следить за статьями тех авторов," +
                    "которые тебе интересны!";

    public StartCommand(SendBotMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void execute(Update update) {
        messageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}
