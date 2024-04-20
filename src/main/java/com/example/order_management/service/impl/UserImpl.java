package com.example.order_management.service.impl;

import com.example.order_management.model.User;
import com.example.order_management.model.exception.UnauthorizedException;
import com.example.order_management.repository.UserRepository;
import com.example.order_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public String generateToken(String email, String password) {

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("O email e a senha são obrigatórios.");
        }

        // Verificar o usuário e a senha
        if (isValidUser(email, password)) {
            // Gerar token JWT

            Authentication authentication =
                    authenticationManager
                            .authenticate(new UsernamePasswordAuthenticationToken(
                                    email,
                                    password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Instant now = Instant.now();

            String scope = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));

            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plus(10, ChronoUnit.HOURS))
                    .subject(authentication.getName())
                    .claim("scope", scope)
                    .build();

            return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        } else {
            throw new UnauthorizedException("Credenciais inválidas");
        }
    }

    @Override
    public User createUser(User user) {

        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            throw new IllegalArgumentException("Todos os campos (username, password e email) são obrigatórios.");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Endereço de email já está em uso.");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }


    @Override
    public boolean findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    private boolean isValidUser(String email, String password) {
        User user = userRepository.findByEmailCustomQuery(email);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }


    @Override
    public boolean isUserLoggedIn(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            Instant expiration = jwt.getExpiresAt();
            return expiration != null && expiration.isAfter(Instant.now());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String extractEmailFromToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            Map<String, Object> claims = jwt.getClaims();
            return (String) claims.get("sub");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao extrair o email do token", e);
        }
    }

}

