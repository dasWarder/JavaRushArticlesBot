package com.warder.jrtb.service.groupSub;

import com.warder.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.warder.jrtb.repository.entity.GroupSub;

public interface GroupSubService {

    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
}
