package com.doctor.api.kauan_doctor.model.login;

public record LoginResponseDTO(
        String token,
        String role,
        String nome
) {
}
