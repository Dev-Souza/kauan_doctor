package com.doctor.api.kauan_doctor.repository;

import com.doctor.api.kauan_doctor.model.medico.MedicoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<MedicoModel, Long> {
    Optional<MedicoModel> findByEmail(String email);
}
