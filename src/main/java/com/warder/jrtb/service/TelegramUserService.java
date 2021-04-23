package com.warder.jrtb.service;

import com.warder.jrtb.repository.users.TelegramUser;
import java.util.*;


public interface TelegramUserService {

    void save(TelegramUser user);

    List<TelegramUser> retrieveAllActiveUsers();

    Optional<TelegramUser> findByChatId(String chatId);
}
