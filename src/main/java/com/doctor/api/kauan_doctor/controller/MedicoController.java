package com.doctor.api.kauan_doctor.controller;

import com.doctor.api.kauan_doctor.model.medico.MedicoRequestDTO;
import com.doctor.api.kauan_doctor.model.medico.MedicoResponseDTO;
import com.doctor.api.kauan_doctor.service.MedicoService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicos")
@RequiredArgsConstructor
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    // CREATE MEDICO
    @PostMapping
    public ResponseEntity<MedicoResponseDTO> createMedico (@RequestBody MedicoRequestDTO medicoRequestDTO) {return medicoService.createMedico(medicoRequestDTO);}

}
