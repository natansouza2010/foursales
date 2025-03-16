package com.example.product_service_foursales_system.modules.jwt.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String apiSecret;

    public DecodedJWT validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(apiSecret);
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token);

        String role = decodedJWT.getClaim("role").asString();


        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                decodedJWT.getSubject(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + role))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Usuário autenticado: " + auth.getName());
        System.out.println("Roles atribuídas: " + auth.getAuthorities());
        return decodedJWT;
    }
}
