package com.warder.jrtb.repository.users;


import com.warder.jrtb.repository.subs.GroupSub;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Data
@Entity
@Table(name = "tg_user")
public class TelegramUser {

    @Id
    @Column(name = "chat_id")
    private String chat_id;


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
