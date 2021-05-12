package com.warder.jrtb.service.groupSub;

import com.warder.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.warder.jrtb.repository.entity.GroupSub;
import com.warder.jrtb.repository.entity.TelegramUser;
import com.warder.jrtb.repository.subs.GroupSubRepository;
import com.warder.jrtb.service.user.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;


@DisplayName(value = "Unit-level testing for GroupSubService")
class GroupSubServiceTest {

    private GroupSubService groupSubService;
    private GroupSubRepository groupSubRepository;
    private TelegramUser newUser;

    private final static String CHAT_ID = "1";

    @BeforeEach
    public void init() {
        TelegramUserService userService = Mockito.mock(TelegramUserService.class);
        groupSubRepository = Mockito.mock(GroupSubRepository.class);
        groupSubService = new GroupSubServiceImpl(groupSubRepository, userService);

        newUser = new TelegramUser();
        newUser.setChatId(CHAT_ID);
        newUser.setActive(true);

        Mockito.when(userService.findByChatId(CHAT_ID)).thenReturn(Optional.of(newUser));
    }

    @Test
    public void shouldProperlySaveGroup() {
        GroupDiscussionInfo groupDiscussionInfo = new GroupDiscussionInfo();
        groupDiscussionInfo.setId(1);
        groupDiscussionInfo.setTitle("g1");

        GroupSub expectedGroupSub = new GroupSub();
        expectedGroupSub.setId(groupDiscussionInfo.getId());
        expectedGroupSub.setTitle(groupDiscussionInfo.getTitle());
        expectedGroupSub.addUser(newUser);

        groupSubService.save(CHAT_ID, groupDiscussionInfo);
        Mockito.verify(groupSubRepository).save(expectedGroupSub);
    }

    @Test
    public void shouldProperlyAddUserToExistingGroup() {

        TelegramUser oldUser = new TelegramUser();
        oldUser.setActive(true);
        oldUser.setChatId("2");

        GroupDiscussionInfo groupDiscussionInfo = new GroupDiscussionInfo();
        groupDiscussionInfo.setId(1);
        groupDiscussionInfo.setTitle("g2");

        GroupSub groupFromDB = new GroupSub();
        groupFromDB.setId(groupDiscussionInfo.getId());
        groupFromDB.setTitle(groupDiscussionInfo.getTitle());
        groupFromDB.addUser(oldUser);

        Mockito.when(groupSubRepository.findById(groupDiscussionInfo.getId())).thenReturn(Optional.of(groupFromDB));

        GroupSub expectedGroupSub = new GroupSub();
        expectedGroupSub.setId(groupDiscussionInfo.getId());
        expectedGroupSub.setTitle(groupDiscussionInfo.getTitle());
        expectedGroupSub.addUser(oldUser);
        expectedGroupSub.addUser(newUser);

        groupSubService.save(CHAT_ID, groupDiscussionInfo);

        Mockito.verify(groupSubRepository).findById(groupDiscussionInfo.getId());
        Mockito.verify(groupSubRepository).save(expectedGroupSub);
    }



}