package com.example.demo.security_config;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenUtil {

    private final static String ACCES_TOKEN_SECRET = "as7655ss7GAPHSBhs034mKgIYV64838mgsJGG88bjil12";
    private final static Long ACCES_TOKEN_VALIDIY_SECONS = 2_592_000L;

    //agregar la lista de GrabtedAutority como parametro de entrada en el metodo
    //para despues agregarla al Map extra como un claim adicional
    public static String createToken(String name, String email) {
        long expirationTime = ACCES_TOKEN_VALIDIY_SECONS * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("name", name);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCES_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCES_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            //extraer la lista de GrantedAutority para despues pasarla en el constructor del return
            //String name = claims.get("name", String.class);

            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException e) {
            return null;
        }
    }
}