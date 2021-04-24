package com.warder.jrtb.command;

public enum CommandName {

    START("/start"),
    HELP("/help"),
    STOP("/stop"),
    NO(""),
    STAT("/stat"),
    ADD_GROUP_SUB("/addgroupsub"),
    LIST_GROUP_SUB("/listgroupsub");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
