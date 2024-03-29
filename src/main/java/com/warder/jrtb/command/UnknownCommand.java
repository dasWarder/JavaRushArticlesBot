package com.warder.jrtb.command;

import com.warder.jrtb.service.sendMessage.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknownCommand implements Command {

    private final SendBotMessageService messageService;

    public static final String UNKNOWN_MESSAGE = "Ваша команда не понятна для меня!";

    public UnknownCommand(SendBotMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void execute(Update update) {
        messageService.sendMessage(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE);
    }
}
