package com.doctor.api.kauan_doctor.controller;

import com.doctor.api.kauan_doctor.model.medico.MedicoRequestDTO;
import com.doctor.api.kauan_doctor.model.medico.MedicoResponseDTO;
import com.doctor.api.kauan_doctor.service.MedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
@RequiredArgsConstructor
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    // CREATE MEDICO
    @PostMapping
    public ResponseEntity<MedicoResponseDTO> createMedico (@RequestBody MedicoRequestDTO medicoRequestDTO) {return medicoService.createMedico(medicoRequestDTO);}

    // GET ALL MEDICOS
    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> getAllMedicos () {return medicoService.getAllMedicos();}

    // GET BY ID MÉDICO
    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> getById(@PathVariable Long id) {return medicoService.getMedicoById(id);}

    // UPDATE MÉDICO
    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> updateMedico(@PathVariable Long id, @RequestBody MedicoRequestDTO dto) {return medicoService.updateMedico(id, dto);}

    // DELETE MÉDICO
    @DeleteMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> deleteMedico(@PathVariable Long id) {return medicoService.deleteMedico(id);}
}
