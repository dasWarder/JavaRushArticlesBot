package com.warder.jrtb.command;

import com.warder.jrtb.repository.entity.GroupSub;
import com.warder.jrtb.repository.entity.TelegramUser;
import com.warder.jrtb.service.sendMessage.SendBotMessageService;
import com.warder.jrtb.service.user.TelegramUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.warder.jrtb.command.CommandName.LIST_GROUP_SUB;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName(value = "Unit-level testing for ListGroupCommand")
class ListGroupSubCommandTest {

    @Test
    public void shouldProperlyShowListGroupSub() {
        TelegramUser user = new TelegramUser();
        user.setActive(true);
        user.setChatId("1");

        List<GroupSub> groupSubList = new ArrayList<>();

        for(int i = 1; i < 5; i++) {
            groupSubList.add(populateGroupSub(i, "gs" + i));
        }

        user.setSubscribes(groupSubList);

        SendBotMessageService messageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService userService = Mockito.mock(TelegramUserService.class);

        Mockito.when(userService.findByChatId(user.getChatId())).thenReturn(Optional.of(user));

        ListGroupSubCommand command = new ListGroupSubCommand(messageService, userService);

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(Long.valueOf(user.getChatId()));
        Mockito.when(message.getText()).thenReturn(LIST_GROUP_SUB.getCommandName());
        update.setMessage(message);

        String collectedGroups = user.getSubscribes().stream()
                .map(sub -> String.format("%s - %s\n", sub.getTitle(), sub.getId()))
                .collect(Collectors.joining());

        command.execute(update);

        Mockito.verify(messageService).sendMessage(user.getChatId(), collectedGroups);

    }

    private GroupSub populateGroupSub(Integer id, String title) {
        GroupSub gs = new GroupSub();
        gs.setId(id);
        gs.setTitle(title);

        return gs;
    }

}