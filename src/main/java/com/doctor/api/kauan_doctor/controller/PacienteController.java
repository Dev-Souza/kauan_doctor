package com.doctor.api.kauan_doctor.controller;

import com.doctor.api.kauan_doctor.model.paciente.PacienteRequestDTO;
import com.doctor.api.kauan_doctor.model.paciente.PacienteResponseDTO;
import com.doctor.api.kauan_doctor.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    // CREATE PACIENTE
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> createPaciente (@RequestBody PacienteRequestDTO pacienteRequestDTO) {return pacienteService.createPaciente(pacienteRequestDTO);}
}
