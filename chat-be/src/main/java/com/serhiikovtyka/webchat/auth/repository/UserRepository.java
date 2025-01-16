package com.serhiikovtyka.webchat.auth.repository;

import com.serhiikovtyka.webchat.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
