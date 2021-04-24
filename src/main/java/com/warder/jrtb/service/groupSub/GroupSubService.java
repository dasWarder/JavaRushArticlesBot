package com.warder.jrtb.service.groupSub;

import com.warder.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.warder.jrtb.repository.entity.GroupSub;

import java.util.Optional;

public interface GroupSubService {

    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
    GroupSub save(GroupSub sub);
    Optional<GroupSub> findById(Integer id);
}
