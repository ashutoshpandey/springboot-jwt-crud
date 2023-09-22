package com.ashutosh.springsecurity;

import com.ashutosh.springsecurity.models.User;
import com.ashutosh.springsecurity.repository.UserRepository;
import com.ashutosh.springsecurity.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetUserById(){
        User user = new User(1L, "Ashutosh", "12345", "ashutosh@ashutosh.com", null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(1L);

        assertNotEquals(true, foundUser.isEmpty());
        assertEquals(true, foundUser.get().getId() == 1L);
        assertEquals(true, foundUser.get().getName().equals("Ashutosh"));
    }

    @Test
    public void testSaveUser(){
        User user = new User(1L, "Ashutosh", "12345", "ashutosh@ashutosh.com", null);

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotEquals(true, (savedUser == null));
        assertEquals(true, savedUser.getId() == 1L);
        assertEquals(true, savedUser.getName().equals("Ashutosh"));
    }
}
