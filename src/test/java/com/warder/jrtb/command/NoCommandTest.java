package com.warder.jrtb.command;

import static org.junit.jupiter.api.Assertions.*;
import static com.warder.jrtb.command.NoCommand.*;
import static com.warder.jrtb.command.CommandName.*;

class NoCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return NO.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return NO_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new NoCommand(messageService);
    }
}