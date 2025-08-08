package com.doctor.api.kauan_doctor.model.agenda;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendaRequestDTO(
        LocalDate dataAgenda,
        LocalTime horaInicio,
        LocalTime horaFim,
        boolean disponivel,
        StatusAgendaEnum statusAgenda,
        Long medico_id
) {
}
