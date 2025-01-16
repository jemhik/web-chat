package com.serhiikovtyka.webchat.controller;

import com.serhiikovtyka.webchat.auth.controller.UserController;
import com.serhiikovtyka.webchat.auth.dto.LoginRequest;
import com.serhiikovtyka.webchat.auth.dto.LoginResponse;
import com.serhiikovtyka.webchat.auth.dto.RegisterRequest;
import com.serhiikovtyka.webchat.auth.model.User;
import com.serhiikovtyka.webchat.auth.service.UserService;
import com.serhiikovtyka.webchat.auth.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUserSuccess() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("newUser");
        registerRequest.setPassword("password");

        when(userService.findByUsername("newUser")).thenReturn(null);

        ResponseEntity<?> response = userController.registerUser(registerRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    public void testRegisterUserUsernameTaken() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("existingUser");
        registerRequest.setPassword("password");

        User existingUser = new User();
        when(userService.findByUsername("existingUser")).thenReturn(existingUser);

        ResponseEntity<?> response = userController.registerUser(registerRequest);
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Username is already taken", response.getBody());
    }

    @Test
    public void testLoginUserSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("validUser");
        loginRequest.setPassword("password");

        User authenticatedUser = new User();
        authenticatedUser.setUsername("authenticatedUser");
        authenticatedUser.setPassword("password");
        when(userService.authenticateUser("validUser", "password")).thenReturn(authenticatedUser);
        when(jwtTokenUtil.generateAccessToken("authenticatedUser")).thenReturn("token");

        ResponseEntity<?> response = userController.loginUser(loginRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        LoginResponse loginResponse = (LoginResponse) response.getBody();
        assertNotNull(loginResponse);
        assertEquals("token", loginResponse.getToken());
    }

    @Test
    public void testLoginUserInvalidCredentials() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("invalidUser");
        loginRequest.setPassword("wrongPassword");

        when(userService.authenticateUser("invalidUser", "wrongPassword")).thenReturn(null);

        ResponseEntity<?> response = userController.loginUser(loginRequest);
        assertNotNull(response);
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid username or password", response.getBody());
    }
}
