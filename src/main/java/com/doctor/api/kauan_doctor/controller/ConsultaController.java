package com.doctor.api.kauan_doctor.controller;

import com.doctor.api.kauan_doctor.model.consulta.ConsultaRequestDTO;
import com.doctor.api.kauan_doctor.model.consulta.ConsultaResponseDTO;
import com.doctor.api.kauan_doctor.model.consulta.StatusConsultaEnum;
import com.doctor.api.kauan_doctor.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    // CREATE CONSULTA
    @PreAuthorize("hasRole('PACIENTE')")
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> createConsulta(@RequestBody ConsultaRequestDTO consultaRequestDTO) {return consultaService.createConsulta(consultaRequestDTO);}

    //  PEGANDO CONSULTAS DE UM MÉDICO ESPECÍFICO
    @PreAuthorize("hasRole('MEDICO')")
    @GetMapping("medico/{medico_id}")
    public ResponseEntity<List<ConsultaResponseDTO>> listaConsultas(@PathVariable("medico_id") Long medico_id) {return consultaService.listConsultasSpecificMedicoId(medico_id);}

    // PEGANDO HISTÓRICO DE CONSULTAS DE UM PACIENTE POR STATUS
    @PreAuthorize("hasRole('PACIENTE')")
    @GetMapping("pacinente/{paciente_id}/status/{status}")
    public ResponseEntity<List<ConsultaResponseDTO>> listaConsultasPacientePorStatus(@PathVariable("paciente_id") Long paciente_id, @PathVariable("status")StatusConsultaEnum status) {return consultaService.listaHistoricoConsultasPacientePorStatus(paciente_id, status);}
}
