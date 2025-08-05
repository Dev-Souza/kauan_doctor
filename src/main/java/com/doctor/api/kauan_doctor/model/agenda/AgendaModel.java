package com.doctor.api.kauan_doctor.model.agenda;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "agenda")
@Getter
@Setter
@Entity
public class AgendaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataAgenda;
    private LocalTime horaAgenda;

    private boolean status = true;

    // Relacionamentos
}
