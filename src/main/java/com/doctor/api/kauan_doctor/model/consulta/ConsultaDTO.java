package com.doctor.api.kauan_doctor.model.consulta;

public record ConsultaDTO(
        Long id,
        Long medico_id,
        Long paciente_id,
        Long agenda_id
) {
}
