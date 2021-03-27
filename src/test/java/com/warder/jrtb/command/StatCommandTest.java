package com.warder.jrtb.command;

import org.junit.jupiter.api.Test;

import static com.warder.jrtb.command.CommandName.STAT;
import static org.junit.jupiter.api.Assertions.*;

class StatCommandTest extends AbstractCommandTest {


    @Override
    String getCommandName() {
        return STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return String.format(StatCommand.STAT_TEXT, 0);
    }

    @Override
    Command getCommand() {
        return new StatCommand(messageService, userService);
    }
}