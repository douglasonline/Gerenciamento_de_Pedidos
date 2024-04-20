package com.example.order_management.service;

import com.example.order_management.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    String generateToken(String email, String password);

    User createUser(User user);

    boolean findByUsername(String username);

    boolean isUserLoggedIn(String token);

    String extractEmailFromToken(String token);

}
