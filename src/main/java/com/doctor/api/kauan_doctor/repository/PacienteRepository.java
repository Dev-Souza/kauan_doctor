package com.doctor.api.kauan_doctor.repository;

import com.doctor.api.kauan_doctor.model.paciente.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<PacienteModel, Long> {
    Optional<PacienteModel> findByEmail(String email);
}
