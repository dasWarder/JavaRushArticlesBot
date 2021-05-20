package com.warder.jrtb.javarushclient.postClient;

import com.warder.jrtb.javarushclient.dto.post.PostInfo;

import java.util.List;

public interface JavaRushPostClient {
    List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId);
}
