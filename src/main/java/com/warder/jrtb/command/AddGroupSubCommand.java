package com.warder.jrtb.command;

import com.warder.jrtb.javarushclient.groupClient.JavaRushGroupClient;
import com.warder.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.warder.jrtb.javarushclient.dto.GroupRequestArgs;
import com.warder.jrtb.repository.entity.GroupSub;
import com.warder.jrtb.service.groupSub.GroupSubService;
import com.warder.jrtb.service.sendMessage.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.warder.jrtb.command.CommandName.*;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;


public class AddGroupSubCommand implements Command{

    private final SendBotMessageService messageService;
    private final JavaRushGroupClient groupClient;
    private final GroupSubService groupSubService;

    public AddGroupSubCommand(SendBotMessageService messageService, JavaRushGroupClient groupClient, GroupSubService groupSubService) {
        this.messageService = messageService;
        this.groupClient = groupClient;
        this.groupSubService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String message = update.getMessage().getText();

        if(message.equalsIgnoreCase(ADD_GROUP_SUB.getCommandName())) {
            sendGroupIdList(chatId);
            return;
        }

        String groupId = message.split(SPACE)[1];

        if(isNumeric(groupId)) {
            GroupDiscussionInfo groupById = groupClient.getGroupById(Integer.parseInt(groupId));
            if(isNull(groupById.getId())) {
                sendGroupNotFound(chatId, groupId);
            }
            GroupSub savedGroupSub = groupSubService.save(chatId, groupById);
            messageService.sendMessage(chatId, "Подписал на группу " + savedGroupSub.getTitle());
        } else {
            sendGroupNotFound(chatId, groupId);
        }
    }

    private void sendGroupIdList(String chatId) {
        String groupIds = groupClient.getGroupList(GroupRequestArgs.builder().build()).stream()
                .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());

        String message = "Чтобы подписаться на группу - передай комадну вместе с ID группы. \n" +
                "Например: /addGroupSub 16. \n\n" +
                "я подготовил список всех групп - выберай какую хочешь :) \n\n" +
                "имя группы - ID группы \n\n" +
                "%s";

        messageService.sendMessage(chatId, String.format(message, groupIds));
    }

    private void sendGroupNotFound(String chatId, String groupId) {
        String groupNotFoundMessage = "Нет группы с ID = \"%s\"";
        messageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }
}
