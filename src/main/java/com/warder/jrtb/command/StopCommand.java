package com.warder.jrtb.command;

import com.warder.jrtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements Command {
    private final SendBotMessageService messageService;

    public static final String STOP_MESSAGE = "Деактивировал все ваши подписки!";

    public StopCommand(SendBotMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void execute(Update update) {
        messageService.sendMessage(update.getMessage().getChatId().toString(), STOP_MESSAGE);
    }
}
