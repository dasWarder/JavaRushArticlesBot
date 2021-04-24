package com.warder.jrtb.command;

import com.warder.jrtb.service.sendMessage.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.warder.jrtb.command.CommandName.*;

public class HelpCommand implements Command {

    private final SendBotMessageService messageService;

    public static final String HELP_MESSAGE = String.format("<b> Список комманд: </b>\n\n" +
            "%s - получить помощь\n" +
            "<b> Команды, чтобы начать\\закончить работу</b>\n" +
            "%s - начать работу\n" +
            "%s - приостановить работу\n" +
            "%s - получить статистику\n" +
            "%s - посмотреть список групп, доступных для подписки\n" +
            "%s <i> номер группы</i> - подписаться на группу с указанным ID\n" +
            "%s - посмотреть текущие подписки\n",
            HELP.getCommandName(), START.getCommandName(), STOP.getCommandName(), STAT.getCommandName(),
            ADD_GROUP_SUB.getCommandName(), ADD_GROUP_SUB.getCommandName(), LIST_GROUP_SUB.getCommandName());

    public HelpCommand(SendBotMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void execute(Update update) {
        messageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
