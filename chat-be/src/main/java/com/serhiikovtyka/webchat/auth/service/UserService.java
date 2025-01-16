package com.serhiikovtyka.webchat.auth.service;

import com.serhiikovtyka.webchat.auth.model.User;

public interface UserService {

    User registerUser(String username, String password);

    User findByUsername(String username);

    User authenticateUser(String username, String password);
}
