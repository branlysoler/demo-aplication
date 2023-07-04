package com.example.authservice.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtnProvider {

    @Value("${jwt.secret}")
    private String acces_token_secret;

    @Value("${jwt.validity_seconds}")
    private String acces_token_validity_seconds;

    // agregar la lista de GrabtedAutority como parametro de entrada en el metodo
    // para despues agregarla al Map extra como un claim adicional
    public String createToken(String username, String email) {
        long expirationTime = Long.valueOf(acces_token_validity_seconds) * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("email", email);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(acces_token_secret.getBytes()))
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(acces_token_secret.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(acces_token_secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getSubject();
        } catch (JwtException e) {
            return "Bad Request";
        }
    }
}