package com.warder.jrtb.repository.subs;


import com.warder.jrtb.repository.users.TelegramUser;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Data
@Entity
@Table(name = "group_sub")
public class GroupSub {

    @Id
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "last_article_id")
    private Integer lastArticleId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_x_user",
            joinColumns = @JoinColumn(name = "group_sub_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<TelegramUser> users;

    public GroupSub() {};

    public void addUser(TelegramUser telegramUser) {
        if(isNull(users)) {
            users = new ArrayList<>();
        }

        users.add(telegramUser);
    }
}
