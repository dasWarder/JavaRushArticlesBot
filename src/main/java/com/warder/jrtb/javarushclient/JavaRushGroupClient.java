package com.warder.jrtb.javarushclient;

import com.warder.jrtb.javarushclient.dto.GroupCountRequestArgs;
import com.warder.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.warder.jrtb.javarushclient.dto.GroupRequestArgs;

import java.util.*;


public interface JavaRushGroupClient {
    List getGroupList(GroupRequestArgs requestArgs);

    List getGroupDiscussionList(GroupRequestArgs requestArgs);

    Integer getGroupCount(GroupCountRequestArgs requestArgs);

    GroupDiscussionInfo getGroupById(Integer id);
}
