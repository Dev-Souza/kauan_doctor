package com.doctor.api.kauan_doctor.model.medico;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "medico")
@Getter
@Setter
@Entity
public class MedicoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String crm;
    private String email;
    private String senha;
}
