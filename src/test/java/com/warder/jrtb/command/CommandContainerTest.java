package com.warder.jrtb.command;

import com.warder.jrtb.javarushclient.JavaRushGroupClient;
import com.warder.jrtb.service.groupSub.GroupSubService;
import com.warder.jrtb.service.sendMessage.SendBotMessageService;
import com.warder.jrtb.service.user.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CommandContainerTest {

    private CommandContainer commandContainer;



    @BeforeEach
    public void init() {
        SendBotMessageService messageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService userService = Mockito.mock(TelegramUserService.class);
        JavaRushGroupClient groupClient = Mockito.mock(JavaRushGroupClient.class);
        GroupSubService groupSubService = Mockito.mock(GroupSubService.class);
        commandContainer = new CommandContainer(messageService, userService, groupClient, groupSubService);
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