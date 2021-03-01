package com.warder.jrtb.command;

import com.warder.jrtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.warder.jrtb.command.CommandName.*;

public class HelpCommand implements Command {

    private final SendBotMessageService messageService;

    public static final String HELP_MESSAGE = String.format("<b> Список комманд: </b>\n\n" +
            "<b> Команды, чтобы начать\\закончить работу</b>\n" +
            "%s - начать работу\n" +
            "%s - приостановить работу\n" +
            "%s - получить помощь\n", START.getCommandName(), STOP.getCommandName(), HELP.getCommandName());

    public HelpCommand(SendBotMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void execute(Update update) {
        messageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
