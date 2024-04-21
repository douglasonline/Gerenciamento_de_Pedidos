package com.example.order_management.configuration;

import com.example.order_management.model.exception.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final List<String> blockedRoutes;

    public JwtTokenFilter(JwtDecoder jwtDecoder, List<String> blockedRoutes) {
        this.jwtDecoder = jwtDecoder;
        this.blockedRoutes = blockedRoutes;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI().substring(request.getContextPath().length());

        // Verifica se a rota está na lista de rotas bloqueadas
        boolean isBlockedRoute = blockedRoutes.stream().anyMatch(path::matches);

        // Se não for uma rota bloqueada, passa direto para o próximo filtro
        if (!isBlockedRoute) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<String> optionalToken = Optional.ofNullable(request.getHeader("Authorization"));

        if (optionalToken.isEmpty() || !optionalToken.get().startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write("Token JWT não fornecido");
            response.getWriter().flush();
            return;
        }

        try {
            String jwtToken = optionalToken.get().substring(7);

            Jwt jwt = jwtDecoder.decode(jwtToken);

            if (jwt.getExpiresAt().isBefore(Instant.now())) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.getWriter().write("Token JWT expirado");
                response.getWriter().flush();
                return;
            }

            Authentication authentication = new JwtAuthenticationToken(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (BadJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write("Token JWT inválido");
            response.getWriter().flush();
        }
    }
}
