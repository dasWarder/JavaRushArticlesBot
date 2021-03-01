package com.warder.jrtb.command;

import static org.junit.jupiter.api.Assertions.*;
import static com.warder.jrtb.command.StartCommand.*;
import static com.warder.jrtb.command.CommandName.*;


class StartCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return START.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return START_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StartCommand(messageService);
    }
}