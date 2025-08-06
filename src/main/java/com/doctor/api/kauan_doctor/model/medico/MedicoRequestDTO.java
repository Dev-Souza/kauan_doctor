package com.doctor.api.kauan_doctor.model.medico;

import com.doctor.api.kauan_doctor.model.utils.Role;

public record MedicoRequestDTO(
        String nome,
        String crm,
        String email,
        String senha
) {
}
