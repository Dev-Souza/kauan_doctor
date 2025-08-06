package com.doctor.api.kauan_doctor.model.paciente;

public record PacienteRequestDTO(
    String nome,
    String email,
    String senha
) {
}
