package com.doctor.api.kauan_doctor.model.auth;

public record LoginRequestDTO(
        String email,
        String senha
) {
}
