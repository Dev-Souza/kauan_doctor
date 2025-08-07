package com.doctor.api.kauan_doctor.infra.security;

import com.doctor.api.kauan_doctor.model.auth.JwtService;
import com.doctor.api.kauan_doctor.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomUserDetailsService customUserDetailsService) throws Exception {
        return http
                // 1. Desabilita a proteção CSRF (padrão para APIs stateless)
                .csrf(csrf -> csrf.disable())

                // 2. Define a política de sessão como stateless (não guarda estado no servidor)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. Configura as regras de autorização para as requisições HTTP
                .authorizeHttpRequests(auth -> auth
                        // Libera o acesso a todos os endpoints de autenticação e documentação
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/v3/api-docs/**",
                                "/webjars/**"
                        ).permitAll()
                        // Libera a criação de médicos e pacientes (cadastro)
                        .requestMatchers(HttpMethod.POST, "/medicos").permitAll()
                        .requestMatchers(HttpMethod.POST, "/pacientes").permitAll()
                        // Exige autenticação para qualquer outra requisição
                        .anyRequest().authenticated()
                )

                // 4. Adiciona seu filtro de JWT para validar o token antes dos filtros do Spring
                .addFilterBefore(new JwtAuthFilter(jwtService, customUserDetailsService), UsernamePasswordAuthenticationFilter.class)

                .build();
    }
}