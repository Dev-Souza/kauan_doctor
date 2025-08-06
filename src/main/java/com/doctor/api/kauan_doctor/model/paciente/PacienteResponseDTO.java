package com.doctor.api.kauan_doctor.model.paciente;

import com.doctor.api.kauan_doctor.model.utils.Role;

public record PacienteResponseDTO(
        String nome,
        String email,
        Role role
) {
}
