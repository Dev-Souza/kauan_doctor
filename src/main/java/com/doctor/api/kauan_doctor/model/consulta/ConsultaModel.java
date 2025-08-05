package com.doctor.api.kauan_doctor.model.consulta;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "consulta")
@Getter
@Setter
@Entity
public class ConsultaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;
    // Criar relacionamentos com médico, paciente e agenda
}
