package com.announcementdesk.services;

import com.announcementdesk.domain.User;
import com.announcementdesk.repositories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest

class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void addUser() {
        User user = new User("serg","aaaa@gmail.com","aaa");

        boolean isUserCreated = userService.addUser(user);

        Assert.assertTrue(isUserCreated);
        Assert.assertNotNull(user.isActive());

        Mockito.verify(userRepository, Mockito.times(1)).save(user);

    }

    @Test
    void failedAddUser(){
        User u = new User("serg","aaaa@gmail.com","aaa");

        Mockito.doReturn(new User("serg","aaaa@gmail.com","aaa"))
                .when(userRepository)
                .findByEmail("aaaa@gmail.com");
        boolean isUserCreated = userService.addUser(u);


        Assert.assertFalse(isUserCreated);

    }

    @Test
    void successFindingUser(){
        User u = new User("sergio","aaaa@gmail.com","aaaaaaa");
        userRepository.save(u);
        Iterable<User> u1 = userRepository.findAll();
        System.out.println(u1);
        //Assert.assertEquals(u.getUsername(),u1.getUsername());
        //Assert.assertEquals(u.getPassword(), u1.getPassword());

    }

}