package com.doctor.api.kauan_doctor.repository;

import com.doctor.api.kauan_doctor.model.agenda.AgendaModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgendaRepository extends JpaRepository<AgendaModel, Long> {

    @Query(value = "SELECT * FROM agenda WHERE medico_id = :medico_id AND status_agenda = 'LIVRE'", nativeQuery = true)
    List<AgendaModel> findAllAgenda(@Param("medico_id") Long medico_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE agenda SET status_agenda = 'RESERVADA' WHERE id = :idAgenda", nativeQuery = true)
    void updateStatusAgenda(@Param("idAgenda") Long idAgenda);
}