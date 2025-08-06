package com.doctor.api.kauan_doctor.model.medico;

import com.doctor.api.kauan_doctor.model.utils.Role;

public record MedicoResponseDTO(
        Long id,
        String nome,
        String email,
        Role role
) {

}
