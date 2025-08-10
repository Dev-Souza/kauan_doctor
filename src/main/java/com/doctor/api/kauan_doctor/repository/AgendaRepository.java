package com.doctor.api.kauan_doctor.repository;

import com.doctor.api.kauan_doctor.model.agenda.AgendaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<AgendaModel, Long> {
}
