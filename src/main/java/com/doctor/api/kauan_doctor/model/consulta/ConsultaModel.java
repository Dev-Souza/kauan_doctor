package com.doctor.api.kauan_doctor.model.consulta;

import com.doctor.api.kauan_doctor.model.agenda.AgendaModel;
import com.doctor.api.kauan_doctor.model.medico.MedicoModel;
import com.doctor.api.kauan_doctor.model.paciente.PacienteModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "consulta")
@Getter
@Setter
@Entity
public class ConsultaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com médico
    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private MedicoModel medico;

    // Relacionamento com Paciente
    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private PacienteModel paciente;

    // Relacionamento com Agenda
    @OneToOne
    @JoinColumn(name = "agenda_id", unique = true)
    private AgendaModel agenda;

    private String observacao;

    @Enumerated(EnumType.STRING)
    private StatusConsultaEnum status;

}