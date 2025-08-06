package com.doctor.api.kauan_doctor.model.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        var auth = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        authManager.authenticate(auth);

        UserDetails user = userDetailsService.loadUserByUsername(dto.email());
        String token = jwtService.gerarToken(user);
        String role = user.getAuthorities().iterator().next().getAuthority();

        return ResponseEntity.ok(new LoginResponseDTO(token, role));
    }
}