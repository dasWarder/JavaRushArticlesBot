package com.warder.jrtb.repository.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Data
@Entity
@Table(name = "tg_user")
@EqualsAndHashCode
public class TelegramUser {

    @Id
    @Column(name = "chat_id")
    private String chatId;


    @Column(name = "active")
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private List<GroupSub> subscribes;

    private void addSubscribers(GroupSub sub) {
        if(isNull(subscribes)) {
            subscribes = new ArrayList<>();
        }

        subscribes.add(sub);
    }
}
