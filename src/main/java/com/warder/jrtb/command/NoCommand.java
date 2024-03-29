package com.warder.jrtb.command;

import com.warder.jrtb.service.sendMessage.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class NoCommand implements Command {

    private final SendBotMessageService messageService;

    public static final String NO_MESSAGE = "Я поддерживаю разные команды.\n" +
            "Для того, чтобы получть помощь наберите /help";

    public NoCommand(SendBotMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void execute(Update update) {
        messageService.sendMessage(update.getMessage().getChatId().toString(), NO_MESSAGE);
    }
}
