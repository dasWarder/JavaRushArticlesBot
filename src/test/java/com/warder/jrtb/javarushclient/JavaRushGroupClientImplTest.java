package com.warder.jrtb.javarushclient;

import com.warder.jrtb.javarushclient.dto.GroupCountRequestArgs;
import com.warder.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.warder.jrtb.javarushclient.dto.GroupRequestArgs;
import com.warder.jrtb.javarushclient.dto.MeGroupInfoType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JavaRushGroupClientImplTest {

    private final JavaRushGroupClient groupClient = new JavaRushGroupClientImpl("https://javarush.ru/api/1.0/rest");


    @Test
    void shouldProperlyGetGroupsWithEmptyArgs() {
        GroupRequestArgs requestArgs = GroupRequestArgs.builder().build();

        List groupList = groupClient.getGroupList(requestArgs);

        Assertions.assertNotNull(groupList);
        Assertions.assertFalse(groupList.isEmpty());
    }

    @Test
    void shouldProperlyGetWithOffsetAndLimit() {
        GroupRequestArgs requestArgs = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();

        List groupList = groupClient.getGroupList(requestArgs);

        Assertions.assertNotNull(groupList);
        Assertions.assertEquals(3, groupList.size());
    }

    @Test
    void shouldProperlyGetGroupsDiscWithEmptyArgs() {

        GroupRequestArgs args = GroupRequestArgs.builder().build();

        List groupList = groupClient.getGroupDiscussionList(args);

        Assertions.assertNotNull(groupList);
        Assertions.assertFalse(groupList.isEmpty());
    }

    @Test
    void shouldProperlyGetGroupDiscWithOffsetAndLimit() {

        GroupRequestArgs requestArgs = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();

        List groupList = groupClient.getGroupDiscussionList(requestArgs);

        Assertions.assertNotNull(groupList);
        Assertions.assertEquals(3, groupList.size());
    }

    @Test
    void shouldProperlyGetGroupCount() {

        GroupCountRequestArgs requestArgs = GroupCountRequestArgs.builder().build();

        Integer groupCount = groupClient.getGroupCount(requestArgs);

        Assertions.assertEquals(30, groupCount);
    }

    @Test
    void shouldProperlyGetGroupTECHCount() {
        GroupCountRequestArgs requestArgs = GroupCountRequestArgs.builder()
                .type(MeGroupInfoType.TECH)
                .build();

        Integer groupCount = groupClient.getGroupCount(requestArgs);

        Assertions.assertEquals(7, groupCount);
    }

    @Test
    void shouldProperlyGetGroupById() {

        Integer androidGroupId = 16;

        GroupDiscussionInfo groupById = groupClient.getGroupById(androidGroupId);

        Assertions.assertNotNull(groupById);
        Assertions.assertEquals(16, groupById.getId());
        Assertions.assertEquals(MeGroupInfoType.TECH, groupById.getType());
        Assertions.assertEquals("android", groupById.getKey());
    }
}