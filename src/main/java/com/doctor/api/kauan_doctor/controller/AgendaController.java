package com.doctor.api.kauan_doctor.controller;

import com.doctor.api.kauan_doctor.model.agenda.AgendaRequestDTO;
import com.doctor.api.kauan_doctor.model.agenda.AgendaResponseDTO;
import com.doctor.api.kauan_doctor.service.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Agendas")
@RequiredArgsConstructor
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    // CREATE AGENDA
    @PreAuthorize("hasRole('MEDICO')")
    @PostMapping
    public ResponseEntity<AgendaResponseDTO> createAgenda (@RequestBody AgendaRequestDTO agendaRequestDTO) {return agendaService.createAgenda(agendaRequestDTO);}
}
