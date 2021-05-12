package com.warder.jrtb.repository.users;

import com.warder.jrtb.repository.entity.GroupSub;
import com.warder.jrtb.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TelegramUserRepositoryIT {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Sql(scripts = {"classpath:/sql/clearDb.sql", "classpath:/sql/initDb.sql"})
    @Test
    public void shouldCorrectFindAllActiveUsers() {

        List<TelegramUser> users = telegramUserRepository.findAllByActiveTrue();

        Assertions.assertEquals(5, users.size());
    }

    @Sql(scripts = "/sql/clearDb.sql")
    @Test
    public void shouldCorrectSaveTgUsers() {
        TelegramUser user = new TelegramUser();
        user.setChatId("1241343868");
        user.setActive(false);

        telegramUserRepository.save(user);

        Optional<TelegramUser> saved = telegramUserRepository.findById(user.getChatId());

        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(user, saved.get());
    }

    @Sql(scripts = {"/sql/clearDb.sql", "/sql/fiveGroupSubsForUser.sql"})
    @Test
    public void shouldProperlyGetAllGroupForUser() {
        Optional<TelegramUser> user = telegramUserRepository.findById("1");

        Assertions.assertTrue(user.isPresent());
        List<GroupSub> subscriptions = user.get().getSubscribes();

        for(int i = 0; i < subscriptions.size(); i++) {
            Assertions.assertEquals(String.format("g%s", (i + 1)), subscriptions.get(i).getTitle());
            Assertions.assertEquals(i + 1, subscriptions.get(i).getId());
            Assertions.assertEquals(i + 1, subscriptions.get(i).getLastArticleId());
        }
    }
}