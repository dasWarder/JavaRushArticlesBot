package com.warder.jrtb.command;

import com.warder.jrtb.service.SendBotMessageService;
import com.warder.jrtb.service.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CommandContainerTest {

    private CommandContainer commandContainer;



    @BeforeEach
    public void init() {
        SendBotMessageService messageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService userService = Mockito.mock(TelegramUserService.class);
        commandContainer = new CommandContainer(messageService, userService);
    }

    @Test
    public void getAllExistCommand() {
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    public void checkUnknownCommand() {
        String unknownCommand = "/dfgdfhdh";

        Command command = commandContainer.retrieveCommand(unknownCommand);
        assertEquals(UnknownCommand.class, command.getClass());
    }

}