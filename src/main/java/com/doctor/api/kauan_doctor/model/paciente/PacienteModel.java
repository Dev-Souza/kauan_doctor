package com.doctor.api.kauan_doctor.model.paciente;

import com.doctor.api.kauan_doctor.model.consulta.ConsultaModel;
import com.doctor.api.kauan_doctor.model.utils.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private Role role = Role.ROLE_PACIENTE;

    // Relacionamento com consulta
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ConsultaModel> consultas = new ArrayList<>();
}