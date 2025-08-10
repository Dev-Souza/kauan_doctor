package com.doctor.api.kauan_doctor.service;

import com.doctor.api.kauan_doctor.model.agenda.AgendaModel;
import com.doctor.api.kauan_doctor.model.agenda.AgendaRequestDTO;
import com.doctor.api.kauan_doctor.model.agenda.AgendaResponseDTO;
import com.doctor.api.kauan_doctor.model.medico.MedicoModel;
import com.doctor.api.kauan_doctor.model.medico.MedicoResponseDTO;
import com.doctor.api.kauan_doctor.repository.AgendaRepository;
import com.doctor.api.kauan_doctor.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    // Conversor: DTO to Entity
    private AgendaModel dtoToEntity(AgendaRequestDTO dto) {
        AgendaModel agendaModel = new AgendaModel();
        agendaModel.setData(dto.dataAgenda());
        agendaModel.setHoraInicio(dto.horaInicio());
        agendaModel.setHoraFim(dto.horaFim());
        agendaModel.setDisponivel(dto.disponivel());
        agendaModel.setStatusAgenda(dto.statusAgenda());

        // GET IF MEDICO IS VALID
        Optional<MedicoModel> medicoBuscado = medicoRepository.findById(dto.medico_id());
        medicoBuscado.ifPresent(agendaModel::setMedico);

        return agendaModel;
    }

    // Conversor: Entity to DTO
    private AgendaResponseDTO entityToDto(AgendaModel agenda) {
        return new AgendaResponseDTO(
                agenda.getId(),
                agenda.getData(),
                agenda.getHoraInicio(),
                agenda.getHoraFim(),
                agenda.isDisponivel(),
                agenda.getStatusAgenda(),
                // GET id do medico
                agenda.getMedico().getId()
        );
    }

    // CREATE AGENDA
    public ResponseEntity<AgendaResponseDTO> createAgenda(AgendaRequestDTO dto) {
        //GET Medico
        Optional<MedicoModel> medicoBuscado = medicoRepository.findById(dto.medico_id());

        // Se vier o médico, salva o registro
        if (medicoBuscado.isPresent()) {
            AgendaModel agendaModel = new AgendaModel();
            agendaModel.setData(dto.dataAgenda());
            agendaModel.setHoraInicio(dto.horaInicio());
            agendaModel.setHoraFim(dto.horaFim());
            agendaModel.setDisponivel(dto.disponivel());
            agendaModel.setStatusAgenda(dto.statusAgenda());
            agendaModel.setMedico(medicoBuscado.get());
            medicoRepository.save(agendaModel.getMedico());
            return ResponseEntity.status(201).body(entityToDto(agendaModel));
        }

        // Dê uma devolutiva caso o médico não exista
        return ResponseEntity.status(404).build();
    }
}
