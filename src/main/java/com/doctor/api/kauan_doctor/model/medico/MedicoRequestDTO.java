package com.doctor.api.kauan_doctor.model.medico;

public record MedicoRequestDTO(
        String nome,
        String crm,
        String email,
        String senha
) {
}
