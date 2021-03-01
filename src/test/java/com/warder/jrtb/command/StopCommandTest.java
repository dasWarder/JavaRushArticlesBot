package com.warder.jrtb.command;

import static org.junit.jupiter.api.Assertions.*;
import static com.warder.jrtb.command.StopCommand.*;
import static com.warder.jrtb.command.CommandName.*;


class StopCommandTest extends AbstractCommandTest{

    @Override
    String getCommandName() {
        return STOP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return STOP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StopCommand(messageService);
    }
}