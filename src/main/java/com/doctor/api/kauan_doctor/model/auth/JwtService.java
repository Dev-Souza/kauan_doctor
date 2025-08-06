package com.doctor.api.kauan_doctor.model.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${api.security.token.secret}")
    private String SECRET_KEY;

    public String gerarToken(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getAuthorities().iterator().next().getAuthority())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 8)) // 8h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extrairUsuario(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValido(String token, UserDetails userDetails) {
        String username = extrairUsuario(token);
        return username.equals(userDetails.getUsername()) && !tokenExpirado(token);
    }

    private boolean tokenExpirado(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}