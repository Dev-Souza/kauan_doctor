package com.doctor.api.kauan_doctor.repository;

import com.doctor.api.kauan_doctor.model.consulta.ConsultaModel;
import com.doctor.api.kauan_doctor.model.consulta.StatusConsultaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<ConsultaModel, Long> {

    @Query(value = "SELECT * FROM consulta WHERE medico_id = :medico_id", nativeQuery = true)
    List<ConsultaModel> listaConsultasDeUmMedico(@Param("medico_id") Long medicoId);

    @Query(value = "SELECT * FROM consulta WHERE paciente_id = :paciente_id AND status = :status", nativeQuery = true)
    List<ConsultaModel> consultasPacientePorStatus(@Param("paciente_id") Long paciente_id, @Param("status") StatusConsultaEnum status);
}
