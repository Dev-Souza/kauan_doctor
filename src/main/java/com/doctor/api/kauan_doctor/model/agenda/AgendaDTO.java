package com.doctor.api.kauan_doctor.model.agenda;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendaDTO(
        Long id,
        LocalDate dataAgenda,
        LocalTime horaAgenda,
        boolean disponivel,
        StatusAgendaEnum statusAgenda,
        Long medico_id
) {
}
