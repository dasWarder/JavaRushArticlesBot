package com.warder.jrtb.command;

import static com.warder.jrtb.command.CommandName.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.warder.jrtb.command.HelpCommand.*;

class HelpCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new HelpCommand(messageService);
    }
}