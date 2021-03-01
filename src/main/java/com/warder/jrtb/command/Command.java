package com.warder.jrtb.command;

import org.telegram.telegrambots.meta.api.objects.Update;

/*
    Command interface for delegation
    Bot Commands from onUpdateReceived
 */
public interface Command {

    // main method for the whole commands
    //@param provides Update object
    void execute(Update update);
}
