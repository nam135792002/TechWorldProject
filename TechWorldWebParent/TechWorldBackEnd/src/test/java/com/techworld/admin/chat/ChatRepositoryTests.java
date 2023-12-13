package com.techworld.admin.chat;

import com.techworld.admin.message.repository.MessageMemberRepository;
import com.techworld.common.entity.MessageMember;
import com.techworld.common.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ChatRepositoryTests {

    @Autowired private MessageMemberRepository repository;

    @Test
    public void testMessageMember(){
        MessageMember messageMember = new MessageMember();

        User from = new User(42);
        User to = new User(43);

        String message = "How are you!";
        messageMember.setMessage(message);
        messageMember.setCreatedTime(new Date());
        messageMember.setFromUser(from);
        messageMember.setToUser(to);

        MessageMember savedMessage = repository.save(messageMember);
        Assertions.assertThat(savedMessage.getId()).isGreaterThan(0);
    }

    @Test
    public void testRepMessageMember(){
        MessageMember messageMember = new MessageMember();

        User to = new User(42);
        User from = new User(43);

        String message = "Hi";
        messageMember.setMessage(message);
        messageMember.setCreatedTime(new Date());
        messageMember.setFromUser(from);
        messageMember.setToUser(to);

        MessageMember savedMessage = repository.save(messageMember);
        Assertions.assertThat(savedMessage.getId()).isGreaterThan(0);
    }
}
