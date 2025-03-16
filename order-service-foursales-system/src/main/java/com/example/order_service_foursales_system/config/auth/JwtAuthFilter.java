package com.example.order_service_foursales_system.config.auth;


import com.example.order_service_foursales_system.config.exception.ValidationException;
import com.example.order_service_foursales_system.modules.jwt.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        try {
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new ValidationException("Authorization header is invalid");
            }

            String token = authorization.substring(7);
            jwtService.validateToken(token);


            filterChain.doFilter(request, response);
        } catch (ValidationException e) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"message\": \"" + e.getMessage() + "\"}");
            response.getWriter().flush();
        }
    }

}