package com.example.order_management.controller;

import com.example.order_management.model.LoginResponse;
import com.example.order_management.model.User;
import com.example.order_management.model.exception.UnauthorizedException;
import com.example.order_management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Operation(summary = "Create a new user")
    @PostMapping("/users")
    @ApiResponse(responseCode = "200", description = "User created")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.ok(newUser);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Erro ao criar usuário: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Falha ao criar usuário", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao criar usuário");
        }
    }

    @Operation(summary = "Generate JWT token")
    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token generated"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> generateToken(@RequestBody User user) {
        try {
            String token = userService.generateToken(user.getEmail(), user.getPassword());
            LoginResponse loginResponse = new LoginResponse("Usuário logado com sucesso", token);
            return ResponseEntity.ok(loginResponse);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Erro ao gerar token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UnauthorizedException e) {
            LOGGER.error("Usuário não autorizado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Erro ao autenticar usuário", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao autenticar usuário");
        }
    }

    @GetMapping("/isLoggedIn")
    public ResponseEntity<?> isLoggedIn(@RequestHeader(name = "Authorization") String token) {
        try {
            boolean loggedIn = userService.isUserLoggedIn(token.replace("Bearer ", ""));
            if (loggedIn) {
                return ResponseEntity.ok("Usuário está logado.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não está logado ou o token expirou.");
            }
        } catch (Exception e) {
            LOGGER.error("Erro ao verificar status de login do usuário", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao verificar status de login do usuário");
        }
    }


}
