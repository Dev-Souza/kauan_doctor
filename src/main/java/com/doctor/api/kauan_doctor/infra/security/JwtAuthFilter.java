package com.doctor.api.kauan_doctor.infra.security;

import com.doctor.api.kauan_doctor.model.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        return (path.equals("/medicos") && method.equals("POST")) ||
                (path.equals("/pacientes") && method.equals("POST")) ||
                (path.equals("/auth/login") && method.equals("POST")) ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/swagger-ui.html") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/webjars") ||
                path.equals("/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String username = jwtService.extrairUsuario(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserSecurity userDetails = (UserSecurity) userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValido(token, userDetails)) {
                // Extrai a autoridade com prefixo ROLE_
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                List.of(new SimpleGrantedAuthority(userDetails.getRole()))
                        );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
