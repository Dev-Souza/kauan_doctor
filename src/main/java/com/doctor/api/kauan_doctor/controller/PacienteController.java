package com.doctor.api.kauan_doctor.controller;

import com.doctor.api.kauan_doctor.model.paciente.PacienteRequestDTO;
import com.doctor.api.kauan_doctor.model.paciente.PacienteResponseDTO;
import com.doctor.api.kauan_doctor.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    // CREATE PACIENTE
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> createPaciente (@RequestBody PacienteRequestDTO pacienteRequestDTO) {return pacienteService.createPaciente(pacienteRequestDTO);}

    // GET ALL PACIENTES
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> getAllPacientes() {return pacienteService.getAllPacientes();}

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> getPacienteById(@PathVariable("id") Long id) {return pacienteService.getPacienteById(id);}

    // UPDATE PACIENTE
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> updatePaciente(@PathVariable("id") Long id, @RequestBody PacienteRequestDTO dto){return pacienteService.updatePaciente(id, dto);}

    // DELETE PACIENTE
    @DeleteMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> deletePaciente(@PathVariable("id") Long id) {return pacienteService.deletePaciente(id);}
}
