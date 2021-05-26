package com.warder.jrtb.service.scheduler;

import com.warder.jrtb.javarushclient.dto.post.PostInfo;
import com.warder.jrtb.javarushclient.postClient.JavaRushPostClient;
import com.warder.jrtb.repository.entity.GroupSub;
import com.warder.jrtb.repository.entity.TelegramUser;
import com.warder.jrtb.service.groupSub.GroupSubService;
import com.warder.jrtb.service.sendMessage.SendBotMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FindNewArticleServiceImpl implements FindNewArticleService{

    public static final String JAVARUSH_WEB_POST_FORMAT = "https://javarush.ru/groups/posts/%s";

    private final GroupSubService groupSubService;
    private final JavaRushPostClient javaRushPostClient;
    private final SendBotMessageService sendMessageService;

    @Autowired
    public FindNewArticleServiceImpl(GroupSubService groupSubService,
                                     JavaRushPostClient javaRushPostClient,
                                     SendBotMessageService sendMessageService) {
        this.groupSubService = groupSubService;
        this.javaRushPostClient = javaRushPostClient;
        this.sendMessageService = sendMessageService;
    }


    @Override
    public void findNewArticles() {
        groupSubService.findAll()
                .forEach(sub -> {
                    List<PostInfo> newPosts = javaRushPostClient.findNewPosts(sub.getId(), sub.getLastArticleId());

                    setNewLastArticleId(sub, newPosts);

                    notifySubscribersAboutNewArticles(sub, newPosts);
                });
    }

    private void setNewLastArticleId(GroupSub sub, List<PostInfo> newPosts) {
        newPosts.stream()
                .mapToInt(PostInfo::getId).max()
                .ifPresent(id -> {
                    sub.setLastArticleId(id);
                    groupSubService.save(sub);
                });
    }

    private void notifySubscribersAboutNewArticles(GroupSub sub, List<PostInfo> newPosts) {
        Collections.reverse(newPosts);
        List<String> messagesWithNewArticles = newPosts.stream()
                .map(post -> String.format("Вышла новая статья <b>%s</b> в группе <b>%s</b>. \n\n" +
                                "<b>Описание:</b>%s\n\n" +
                                "<b>Ссылка:</b>%s\n",
                        post.getTitle(), sub.getTitle(), post.getDescription(), getPostUrl(post.getKey())))
                .collect(Collectors.toList());

        sub.getUsers().stream()
                .filter(TelegramUser::isActive)
                .forEach(it -> sendMessageService.sendMessage(it.getChatId(), messagesWithNewArticles));
    }

    private String getPostUrl(String key) {
        return String.format(JAVARUSH_WEB_POST_FORMAT, key);
    }

}
