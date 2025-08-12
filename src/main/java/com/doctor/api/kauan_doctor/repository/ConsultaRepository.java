package com.doctor.api.kauan_doctor.repository;

import com.doctor.api.kauan_doctor.model.consulta.ConsultaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<ConsultaModel, Long> {
}
