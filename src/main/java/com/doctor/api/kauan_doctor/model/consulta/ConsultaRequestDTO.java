package com.doctor.api.kauan_doctor.model.consulta;

public record ConsultaRequestDTO(
        Long medico_id,
        Long paciente_id,
        Long agenda_id,
        String observacao,
        StatusConsultaEnum status
) {
}
