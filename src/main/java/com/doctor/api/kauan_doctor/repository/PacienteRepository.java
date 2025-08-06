package com.doctor.api.kauan_doctor.repository;

import com.doctor.api.kauan_doctor.model.paciente.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<PacienteModel, Long> {
}
