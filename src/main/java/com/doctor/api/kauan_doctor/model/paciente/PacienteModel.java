package com.doctor.api.kauan_doctor.model.paciente;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "paciente")
@Getter
@Setter
@Entity
public class PacienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String role;
}
