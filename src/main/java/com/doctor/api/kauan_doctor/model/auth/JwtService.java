package com.doctor.api.kauan_doctor.model.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${api.security.token.secret}")
    private String SECRET_KEY;

    public String gerarToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role", user.getAuthorities().iterator().next().getAuthority())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 8))
                .signWith(getChaveDeAssinatura())
                .compact();
    }

    public String extrairUsuario(String token) {
        return extrairClaim(token, Claims::getSubject);
    }

    public boolean isTokenValido(String token, UserDetails userDetails) {
        final String username = extrairUsuario(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpirado(token);
    }

    private boolean isTokenExpirado(String token) {
        return extrairClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extrairClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extrairTodosOsClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extrairTodosOsClaims(String token) {
        return Jwts.parser()
                .verifyWith(getChaveDeAssinatura())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getChaveDeAssinatura() {
        byte[] keyBytes = this.SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}