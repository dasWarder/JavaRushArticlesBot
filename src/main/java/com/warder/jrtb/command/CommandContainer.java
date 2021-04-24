package com.warder.jrtb.command;

import com.google.common.collect.ImmutableMap;
import com.warder.jrtb.javarushclient.JavaRushGroupClient;
import com.warder.jrtb.service.groupSub.GroupSubService;
import com.warder.jrtb.service.sendMessage.SendBotMessageService;
import com.warder.jrtb.service.user.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.warder.jrtb.command.CommandName.*;


public class CommandContainer {
    private final ImmutableMap<String, Command> commands;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService messageService, TelegramUserService userService, JavaRushGroupClient groupClient,
                            GroupSubService groupSubService) {
        commands = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(messageService, userService))
                .put(STOP.getCommandName(), new StopCommand(messageService, userService))
                .put(HELP.getCommandName(), new HelpCommand(messageService))
                .put(NO.getCommandName(), new NoCommand(messageService))
                .put(STAT.getCommandName(), new StatCommand(messageService, userService))
                .put(ADD_GROUP_SUB.getCommandName(), new AddGroupSubCommand(messageService, groupClient, groupSubService))
                .put(LIST_GROUP_SUB.getCommandName(), new ListGroupSubCommand(messageService, userService))
                .build();

        unknownCommand = new UnknownCommand(messageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commands.getOrDefault(commandIdentifier, unknownCommand);
    }
}
