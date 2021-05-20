package com.warder.jrtb.javarushclient.groupClient;

import com.warder.jrtb.javarushclient.dto.GroupCountRequestArgs;
import com.warder.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.warder.jrtb.javarushclient.dto.GroupInfo;
import com.warder.jrtb.javarushclient.dto.GroupRequestArgs;

import java.util.*;


public interface JavaRushGroupClient {
    List<GroupInfo> getGroupList(GroupRequestArgs requestArgs);

    List<GroupDiscussionInfo> getGroupDiscussionList(GroupRequestArgs requestArgs);

    Integer getGroupCount(GroupCountRequestArgs requestArgs);

    GroupDiscussionInfo getGroupById(Integer id);
}
