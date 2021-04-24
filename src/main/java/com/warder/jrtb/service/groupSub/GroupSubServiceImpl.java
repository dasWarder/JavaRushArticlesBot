package com.warder.jrtb.service.groupSub;

import com.warder.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.warder.jrtb.repository.entity.GroupSub;
import com.warder.jrtb.repository.entity.TelegramUser;
import com.warder.jrtb.repository.subs.GroupSubRepository;
import com.warder.jrtb.repository.users.TelegramUserRepository;
import com.warder.jrtb.service.user.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class GroupSubServiceImpl implements GroupSubService{

    private final GroupSubRepository groupSubRepository;
    private final TelegramUserService userService;

    @Autowired
    public GroupSubServiceImpl(GroupSubRepository groupSubRepository, TelegramUserService userService) {
        this.groupSubRepository = groupSubRepository;
        this.userService = userService;
    }

    @Override
    public GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo) {
        TelegramUser user = userService.findByChatId(chatId).get();

        GroupSub groupSub;
        Optional<GroupSub> groupSubFromDB = groupSubRepository.findById(groupDiscussionInfo.getId());

        if(groupSubFromDB.isPresent()) {
            groupSub = groupSubFromDB.get();
            Optional<TelegramUser> first = groupSub.getUsers().stream()
                    .filter(u -> u.getChatId().equalsIgnoreCase(chatId))
                    .findFirst();
            if(first.isEmpty()) {
                groupSub.addUser(user);
            }
        } else {
            groupSub = new GroupSub();
            groupSub.addUser(user);
            groupSub.setId(groupDiscussionInfo.getId());
            groupSub.setTitle(groupDiscussionInfo.getTitle());
        }

        return groupSubRepository.save(groupSub);
    }

    @Override
    public GroupSub save(GroupSub sub) {
        return groupSubRepository.save(sub);
    }

    @Override
    public Optional<GroupSub> findById(Integer id) {
        return groupSubRepository.findById(id);
    }
}
