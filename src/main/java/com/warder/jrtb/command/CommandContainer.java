package com.warder.jrtb.command;

import com.google.common.collect.ImmutableMap;
import com.warder.jrtb.service.SendBotMessageService;
import org.springframework.stereotype.Component;

import static com.warder.jrtb.command.CommandName.*;


public class CommandContainer {
    private final ImmutableMap<String, Command> commands;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService messageService) {

        commands = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(messageService))
                .put(STOP.getCommandName(), new StopCommand(messageService))
                .put(HELP.getCommandName(), new HelpCommand(messageService))
                .put(NO.getCommandName(), new NoCommand(messageService))
                .build();

        unknownCommand = new UnknownCommand(messageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commands.getOrDefault(commandIdentifier, unknownCommand);
    }
}
