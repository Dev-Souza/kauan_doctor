package com.doctor.api.kauan_doctor.repository;

import com.doctor.api.kauan_doctor.model.medico.MedicoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<MedicoModel, Long> {
}
