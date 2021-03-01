package com.warder.jrtb.command;

import static org.junit.jupiter.api.Assertions.*;
import static com.warder.jrtb.command.CommandName.*;
import static com.warder.jrtb.command.UnknownCommand.*;


class UnknownCommandTest extends AbstractCommandTest {

    private String wrongCommand = "/fgdfgdfg";

    @Override
    String getCommandName() {
        return wrongCommand;
    }

    @Override
    String getCommandMessage() {
        return UNKNOWN_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new UnknownCommand(messageService);
    }
}