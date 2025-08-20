package com.doctor.api.kauan_doctor.model.auth;

public record LoginResponseDTO(
        String token,
        String role,
        Long idLogado
) {
}
