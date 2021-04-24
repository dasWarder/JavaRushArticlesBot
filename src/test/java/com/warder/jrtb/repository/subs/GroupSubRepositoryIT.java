package com.warder.jrtb.repository.subs;

import com.warder.jrtb.repository.entity.GroupSub;
import com.warder.jrtb.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupSubRepositoryIT {

    @Autowired
    private GroupSubRepository groupSubRepository;

    @Test
    @Sql(scripts = {"/sql/clearDb.sql", "/sql/fiveUsersForGroupSub.sql"})
    public void shouldProperlyGetAllUsersForGroupSub() {

        Optional<GroupSub> sub = groupSubRepository.findById(1);

        Assertions.assertTrue(sub.isPresent());
        Assertions.assertEquals(1, sub.get().getId());
        List<TelegramUser> users = sub.get().getUsers();

        for(int i = 0; i < users.size(); i++) {
            Assertions.assertEquals(String.valueOf(i + 1), users.get(i).getChatId());
            Assertions.assertTrue(users.get(i).isActive());
        }
    }


}