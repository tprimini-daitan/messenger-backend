package com.daitangroup.test;

import com.daitangroup.entity.User;
import com.daitangroup.repo.UserRepository;
import com.daitangroup.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testValidUserCreation() {
        User newUser = new User("1", "user1", "pass1");
        when(userRepository.insert(newUser)).thenReturn(newUser);

        User gotUser = userService.insert(newUser);
        assertEquals("1", gotUser.getId());
        assertEquals("user1", gotUser.getName());
        assertEquals("pass1", gotUser.getPassword());
    }

    @Test
    public void testNullUserCreation() {
        User newUser = null;
        when(userRepository.insert(newUser)).thenReturn(newUser);

        User gotUser = userService.insert(newUser);
        assertNull(gotUser);
    }

    @Test
    public void testUserCreationWithoutId() {
        User newUser = new User(null, "user1", "pass1");
        when(userRepository.insert(newUser)).thenReturn(new User("5b15286608b1961d27bf08ba", "user1", "pass1"));

        User gotUser = userService.insert(newUser);
        assertEquals("5b15286608b1961d27bf08ba", gotUser.getId());
        assertEquals("user1", gotUser.getName());
        assertEquals("pass1", gotUser.getPassword());
    }

    @Test
    public void testUserCreationWithoutName() {
        User newUser = new User("1", null, "pass1");
        when(userRepository.insert(newUser)).thenReturn(newUser);

        User gotUser = userService.insert(newUser);
        assertEquals("1", gotUser.getId());
        assertNull(gotUser.getName());
        assertEquals("pass1", gotUser.getPassword());
    }

    @Test
    public void testUserCreationWithoutPassword() {
        User newUser = new User("1", "user1", null);
        when(userRepository.insert(newUser)).thenReturn(newUser);

        User gotUser = userService.insert(newUser);
        assertEquals("1", gotUser.getId());
        assertEquals("user1", gotUser.getName());
        assertNull(gotUser.getPassword());
    }
}
