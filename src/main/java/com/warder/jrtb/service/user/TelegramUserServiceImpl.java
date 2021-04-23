package com.warder.jrtb.service.user;

import com.warder.jrtb.repository.entity.TelegramUser;
import com.warder.jrtb.repository.users.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelegramUserServiceImpl implements TelegramUserService {

    private final TelegramUserRepository userRepository;

    @Autowired
    public TelegramUserServiceImpl(TelegramUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(TelegramUser user) {
        userRepository.save(user);
    }

    @Override
    public List<TelegramUser> retrieveAllActiveUsers() {
        return userRepository.findAllByActiveTrue();
    }

    @Override
    public Optional<TelegramUser> findByChatId(String chatId) {
        return userRepository.findById(chatId);
    }
}
