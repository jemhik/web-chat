package com.serhiikovtyka.webchat.service;

import com.serhiikovtyka.webchat.auth.model.User;
import com.serhiikovtyka.webchat.auth.repository.UserRepository;
import com.serhiikovtyka.webchat.auth.service.UserService;
import com.serhiikovtyka.webchat.auth.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByUsername() {
        User user = new User(1L, "John Doe", "john.doe@example.com");
        when(userRepository.findByUsername("John Doe")).thenReturn(Optional.of(user));

        User foundUser = userService.findByUsername("John Doe");
        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getUsername());
    }

    @Test
    public void testSaveUser() {
        User inputUser = new User();
        inputUser.setUsername("John Doe");
        inputUser.setPassword("encodedPassword213");

        User savedUser = new User(1L, "John Doe", "encodedPassword213");

        when(passwordEncoder.encode(inputUser.getPassword())).thenReturn("encodedPassword213");
        when(userRepository.save(inputUser)).thenReturn(savedUser);

        User result = userService.registerUser(inputUser.getUsername(), inputUser.getPassword());
        assertNotNull(result, "The saved user should not be null");

        assertEquals("John Doe", result.getUsername(), "The username should be 'John Doe'");
        assertEquals("encodedPassword213", result.getPassword(), "The password should be encoded");
        assertEquals(1L, result.getId(), "The user id should be 1");

    }
}
