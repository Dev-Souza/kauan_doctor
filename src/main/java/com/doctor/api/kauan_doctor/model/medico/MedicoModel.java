package com.doctor.api.kauan_doctor.model.medico;

import com.doctor.api.kauan_doctor.model.agenda.AgendaModel;
import com.doctor.api.kauan_doctor.model.consulta.ConsultaModel;
import com.doctor.api.kauan_doctor.model.utils.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private Role role = Role.ROLE_MEDICO;

    // Relacionamento com agenda
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AgendaModel> agendas = new ArrayList<>();

    // Relacionamento com consulta
    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ConsultaModel> consultas = new ArrayList<>();
}
