package com.warder.jrtb.javarushclient.postClient;

import com.warder.jrtb.javarushclient.dto.post.PostInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.warder.jrtb.util.StaticVariablesStorage.JAVARUSH_API_PATH;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Integration-level testing for JavaRushPostClient")
class JavaRushPostClientTest {

    private final JavaRushPostClient postClient = new JavaRushPostClientImpl(JAVARUSH_API_PATH);

    @Test
    public void shouldProperlyGet15Posts() {

        List<PostInfo> newPosts = postClient.findNewPosts(30, 2935);

        Assertions.assertEquals(15, newPosts.size());
    }
}