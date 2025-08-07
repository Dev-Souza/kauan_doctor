package com.doctor.api.kauan_doctor.model.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    // O UserDetailsService não é mais necessário aqui

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        // 1. Cria o token de autenticação com os dados da requisição
        var authToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());

        // 2. Realiza a autenticação e CAPTURA o resultado
        // O Spring Security faz a mágica de chamar seu UserDetailsService aqui por baixo dos panos
        Authentication authentication = authManager.authenticate(authToken);

        // 3. Extrai o UserDetails do objeto Authentication que foi retornado
        // Este é o usuário que foi autenticado com sucesso
        var user = (UserDetails) authentication.getPrincipal();

        // 4. Gera o token JWT e extrai a role
        String token = jwtService.gerarToken(user);
        String role = user.getAuthorities().iterator().next().getAuthority();

        // 5. Retorna a resposta com o token e a role
        return ResponseEntity.ok(new LoginResponseDTO(token, role));
    }
}