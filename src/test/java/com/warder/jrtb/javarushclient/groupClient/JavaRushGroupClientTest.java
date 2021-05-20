package com.warder.jrtb.javarushclient.groupClient;

import com.warder.jrtb.javarushclient.dto.GroupCountRequestArgs;
import com.warder.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.warder.jrtb.javarushclient.dto.GroupRequestArgs;
import com.warder.jrtb.javarushclient.dto.MeGroupInfoType;
import com.warder.jrtb.javarushclient.groupClient.JavaRushGroupClient;
import com.warder.jrtb.javarushclient.groupClient.JavaRushGroupClientImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.*;

import static com.warder.jrtb.util.StaticVariablesStorage.JAVARUSH_API_PATH;

class JavaRushGroupClientTest {

    private final JavaRushGroupClient groupClient = new JavaRushGroupClientImpl(JAVARUSH_API_PATH);


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