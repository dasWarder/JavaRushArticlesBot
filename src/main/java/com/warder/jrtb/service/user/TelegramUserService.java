package com.warder.jrtb.service.user;

import com.warder.jrtb.repository.entity.TelegramUser;
import java.util.*;


public interface TelegramUserService {

    void save(TelegramUser user);

    List<TelegramUser> retrieveAllActiveUsers();

    Optional<TelegramUser> findByChatId(String chatId);
}
