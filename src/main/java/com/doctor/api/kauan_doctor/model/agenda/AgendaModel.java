package com.doctor.api.kauan_doctor.model.agenda;

import com.doctor.api.kauan_doctor.model.medico.MedicoModel;
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

    private LocalDate data; // Data do atendimento
    private LocalTime horaInicio; // Horário inicial
    private LocalTime horaFim; // Horário final (opcional)

    private boolean disponivel = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusagenda", nullable = false)
    private StatusAgendaEnum statusAgenda; // LIVRE, RESERVADA, CANCELADA...

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private MedicoModel medico;
}