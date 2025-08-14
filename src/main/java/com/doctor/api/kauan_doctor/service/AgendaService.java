package com.doctor.api.kauan_doctor.service;

import com.doctor.api.kauan_doctor.model.agenda.AgendaModel;
import com.doctor.api.kauan_doctor.model.agenda.AgendaRequestDTO;
import com.doctor.api.kauan_doctor.model.agenda.AgendaResponseDTO;
import com.doctor.api.kauan_doctor.model.medico.MedicoModel;
import com.doctor.api.kauan_doctor.repository.AgendaRepository;
import com.doctor.api.kauan_doctor.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Long medicoId = (agenda.getMedico() != null) ? agenda.getMedico().getId() : null;
        return new AgendaResponseDTO(
                agenda.getId(),
                agenda.getData(),
                agenda.getHoraInicio(),
                agenda.getHoraFim(),
                agenda.isDisponivel(),
                agenda.getStatusAgenda(),
                medicoId
        );
    }

    // CREATE AGENDA
    public ResponseEntity<AgendaResponseDTO> createAgenda(AgendaRequestDTO dto) {
        Optional<MedicoModel> medicoBuscado = medicoRepository.findById(dto.medico_id());

        if (medicoBuscado.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        AgendaModel agendaModel = dtoToEntity(dto);
        agendaModel.setMedico(medicoBuscado.get());

        AgendaModel agendaSalva = agendaRepository.save(agendaModel);

        return ResponseEntity.status(201).body(entityToDto(agendaSalva));
    }

    // VER DISPONIBILIDADE DA AGENDA DO MÉDICO
    public ResponseEntity<List<AgendaResponseDTO>> findAllAgendasDeUmMedico(Long medico_id) {
        // BUSCANDO O MÉDICO EXISTENTE
        Optional<MedicoModel> medicoBuscado = medicoRepository.findById(medico_id);

        // CASO EXISTA FAÇA AS OPERAÇÕES
        if (medicoBuscado.isPresent()) {
            // BUSCANDO A LISTA DE AGENDA DO MÉDICO
            List<AgendaResponseDTO> listaAgendaDoMedico = agendaRepository.findAllAgenda(medico_id).stream()
                    .map(this::entityToDto)
                    .toList();
        }

        // CASO O MÉDICO NÃO FOR PRESENTE, RETORNE O ERRO
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // GET ALL AGENDA
    public ResponseEntity<List<AgendaResponseDTO>> getllAgendas() {
        List<AgendaResponseDTO> agendas = agendaRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(agendas);
    }

    // GET AGENDA BY ID
    public ResponseEntity<AgendaResponseDTO> getAgendaById(Long id) {
        // VERIFICA SE A AGENDA EXISTE
        Optional<AgendaModel> agenda = agendaRepository.findById(id);

        // CASO A AGENDA EXISTA, FAÇA A DEVOLUTIVA
        // CASO NÃO EXISTA, ENTREGUE UM RETORNO NEGATIVO
        return agenda.map(agendaModel -> ResponseEntity.status(200).body(entityToDto(agendaModel))).orElseGet(() -> ResponseEntity.status(404).build());
    }

    // UPDATE AGENDA
    public ResponseEntity<AgendaResponseDTO> updateAgenda(Long id, AgendaRequestDTO dto) {
        // VERIFICA SE A AGENDA EXISTE
        Optional<AgendaModel> agenda = agendaRepository.findById(id);

        // CASO EXISTA, FAÇA A OPERAÇÃO
        if(agenda.isPresent()){
            AgendaModel agendaModel = agenda.get();
            agendaModel.setData(dto.dataAgenda());
            agendaModel.setHoraInicio(dto.horaInicio());
            agendaModel.setHoraFim(dto.horaFim());
            agendaModel.setDisponivel(dto.disponivel());
            agendaModel.setStatusAgenda(dto.statusAgenda());

            //BUSCANDO O MÉDICO
            Optional<MedicoModel> medico = medicoRepository.findById(dto.medico_id());

            // CASO O MÉDICO EXISTA
            if(medico.isPresent()){
                agendaModel.setMedico(medico.get());
            } else {
                // MÉDICO NÃO EXISTENTE
                return ResponseEntity.status(404).build();
            }

            AgendaModel agendaSalva = agendaRepository.save(agendaModel);
            return ResponseEntity.status(200).body(entityToDto(agendaSalva));
        }
        // CASO A AGENDA NÃO EXISTA
        return ResponseEntity.status(404).build();
    }

    // DELETE AGENDA
    public ResponseEntity<AgendaResponseDTO> deleteAgenda(Long id) {
        // VERIFICANDO SE A AGENDA EXISTE
        if(agendaRepository.existsById(id)){
            agendaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        // CASO NÃO EXISTA A AGENDA
        return ResponseEntity.status(404).build();
    }
}
