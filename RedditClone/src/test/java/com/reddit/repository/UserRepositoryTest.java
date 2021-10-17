package com.reddit.repository;

import com.reddit.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUserName() {
        //Given
        String userName = "vbele002";
        User user = new User();
        user.setUserId(46544L);
        user.setUserName(userName);
        user.setEmail("vbel@gmail.com");
        user.setPassword("sfsjkhdfh213123");
        userRepository.save(user);

        //When
        List<User> users = userRepository.findAll();
        String actual = userRepository.findByUserName(userName).map(u -> u.getUserName()).get();

        //Then
        String expected = "vbele002";
        assertThat(actual).isEqualTo(expected);
    }
}