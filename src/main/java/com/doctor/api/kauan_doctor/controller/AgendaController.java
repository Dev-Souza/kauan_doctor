package com.doctor.api.kauan_doctor.controller;

import com.doctor.api.kauan_doctor.model.agenda.AgendaRequestDTO;
import com.doctor.api.kauan_doctor.model.agenda.AgendaResponseDTO;
import com.doctor.api.kauan_doctor.service.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendas")
@RequiredArgsConstructor
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    // CREATE AGENDA
    @PreAuthorize("hasRole('MEDICO')")
    @PostMapping
    public ResponseEntity<AgendaResponseDTO> createAgenda (@RequestBody AgendaRequestDTO agendaRequestDTO) {return agendaService.createAgenda(agendaRequestDTO);}

    // VER DISPONIBILIDADE DA AGENDA DE UM MÉDICO
    @PreAuthorize("hasRole('MEDICO')")
    @GetMapping("diponibilidade/medico/{medico_id}")
    public ResponseEntity<List<AgendaResponseDTO>> listaDeAgendaDisponivel(@PathVariable("medico_id") Long medico_id){return agendaService.findAllAgendasDeUmMedico(medico_id);}

    // GET ALL AGENDAS
    @GetMapping
    public ResponseEntity<List<AgendaResponseDTO>> getAllAgendas() {return agendaService.getllAgendas();}

    // GET AGENDA BY ID
    @GetMapping("/{id}")
    public ResponseEntity<AgendaResponseDTO> getAgendaById(@PathVariable("id") Long id) {return agendaService.getAgendaById(id);}

    // UPDATE AGENDA
    @PutMapping("/{id}")
    public ResponseEntity<AgendaResponseDTO> updateAgenda(@PathVariable Long id, @RequestBody AgendaRequestDTO dto) {return agendaService.updateAgenda(id, dto);}

    // DELETE AGENDA
    @DeleteMapping("{id}")
    public ResponseEntity<AgendaResponseDTO> deleteAgenda(@PathVariable Long id) {return agendaService.deleteAgenda(id);}
}
