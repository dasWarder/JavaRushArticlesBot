package com.warder.jrtb.command;

import com.warder.jrtb.repository.entity.GroupSub;
import com.warder.jrtb.repository.entity.TelegramUser;
import com.warder.jrtb.service.groupSub.GroupSubService;
import com.warder.jrtb.service.sendMessage.SendBotMessageService;
import com.warder.jrtb.service.user.TelegramUserService;
import com.warder.jrtb.service.user.TelegramUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.warder.jrtb.command.AbstractCommandTest.*;
import static com.warder.jrtb.command.CommandName.DELETE_GROUP_SUB;
import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName(value = "Unit-level testing for DeleteGroupSubCommand")
class DeleteGroupSubCommandTest {

    private Command command;
    private SendBotMessageService messageService;
    GroupSubService groupSubService;
    TelegramUserService userService;

    @BeforeEach
    public void init() {
        userService = Mockito.mock(TelegramUserService.class);
        groupSubService = Mockito.mock(GroupSubService.class);
        messageService = Mockito.mock(SendBotMessageService.class);

        command = new DeleteGroupSubCommand(groupSubService, userService, messageService);
    }

    @Test
    public void shouldProperlyReturnEmptyGroupSubsList() {
        Long chatId = 23456L;

        Update update = prepareUpdate(chatId, DELETE_GROUP_SUB.getCommandName());

        Mockito.when(userService.findByChatId(String.valueOf(chatId)))
                .thenReturn(Optional.of(new TelegramUser()));

        String expectedMessage = "Пока нет подписок на группы. Чтобы посмотреть доступные команды наберите /help";

        command.execute(update);

        Mockito.verify(messageService).sendMessage(chatId.toString(), expectedMessage);
    }

    @Test
    public void shouldProperlyReturnGroupSubsList() {
        Long chatId = 23456L;
        Update update = prepareUpdate(chatId, DELETE_GROUP_SUB.getCommandName());

        TelegramUser user = new TelegramUser();
        GroupSub sub = new GroupSub();
        sub.setId(123);
        sub.setTitle("GS1 Title");

        user.setSubscribes(singletonList(sub));

        Mockito.when(userService.findByChatId(String.valueOf(chatId)))
                .thenReturn(Optional.of(user));

        String expectedMessage = "Чтобы удалить подписку на группу - передай комадну вместе с ID группы. \n" +
        "Например: /deleteGroupSub 16 \n\n" +
                "я подготовил список всех групп, на которые ты подписан) \n\n" +
                "имя группы - ID группы \n\n" +
                "GS1 Title - 123\n";

        command.execute(update);

        Mockito.verify(messageService).sendMessage(chatId.toString(), expectedMessage);
    }


    @Test
    public void shouldRejectByInvalidGroupId() {
        Long chatId = 23456L;
        Update update = prepareUpdate(chatId, String.format("%s %s", DELETE_GROUP_SUB.getCommandName(), "groupSubId"));

        TelegramUser user = new TelegramUser();
        GroupSub sub = new GroupSub();
        sub.setId(123);
        sub.setTitle("GS1 Title");
        user.setSubscribes(singletonList(sub));

        Mockito.when(userService.findByChatId(chatId.toString()))
                .thenReturn(Optional.of(user));

        String expectedMessage = "Неверный формат ID группы.\n" +
                "ID должно быть целым положительным числом\n";

        command.execute(update);

        Mockito.verify(messageService).sendMessage(String.valueOf(chatId), expectedMessage);
    }

    @Test
    public void shouldProperlyDeleteGroupById() {
        Long chatId = 23456L;
        Integer groupId = 1234;
        Update update = prepareUpdate(chatId, String.format("%s %s", DELETE_GROUP_SUB.getCommandName(), groupId));

        TelegramUser user = new TelegramUser();
        GroupSub sub = new GroupSub();
        sub.setId(groupId);
        sub.setTitle("GS1 Title");
        user.setChatId(String.valueOf(chatId));
        user.setSubscribes(singletonList(sub));

        List<TelegramUser> users = new ArrayList<>();
        users.add(user);
        sub.setUsers(users);

        Mockito.when(groupSubService.findById(groupId))
                .thenReturn(Optional.of(sub));
        Mockito.when(userService.findByChatId(String.valueOf(chatId)))
                .thenReturn(Optional.of(user));


        String expectedMessage = "Удалил подписку на группу: GS1 Title\n";

        command.execute(update);

        Mockito.verify(messageService).sendMessage(String.valueOf(chatId), expectedMessage);
    }


    @Test
    public void shouldDoesNotExistByGroupId() {
        Long chatId = 23456L;
        Integer groupId = 1234;
        Update update = prepareUpdate(chatId, String.format("%s %s", DELETE_GROUP_SUB.getCommandName(), groupId));

        Mockito.when(groupSubService.findById(groupId)).thenReturn(Optional.empty());

        String expectedMessage = "Не нашел такой группы\n";

        command.execute(update);

        Mockito.verify(groupSubService).findById(groupId);
        Mockito.verify(messageService).sendMessage(String.valueOf(chatId), expectedMessage);
    }
}