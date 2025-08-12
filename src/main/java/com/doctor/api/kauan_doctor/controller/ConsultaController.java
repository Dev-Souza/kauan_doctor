package com.doctor.api.kauan_doctor.controller;

import com.doctor.api.kauan_doctor.model.consulta.ConsultaRequestDTO;
import com.doctor.api.kauan_doctor.model.consulta.ConsultaResponseDTO;
import com.doctor.api.kauan_doctor.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
