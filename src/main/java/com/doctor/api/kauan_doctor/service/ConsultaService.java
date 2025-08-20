package com.doctor.api.kauan_doctor.service;

import com.doctor.api.kauan_doctor.model.agenda.AgendaModel;
import com.doctor.api.kauan_doctor.model.consulta.ConsultaModel;
import com.doctor.api.kauan_doctor.model.consulta.ConsultaRequestDTO;
import com.doctor.api.kauan_doctor.model.consulta.ConsultaResponseDTO;
import com.doctor.api.kauan_doctor.model.consulta.StatusConsultaEnum;
import com.doctor.api.kauan_doctor.model.medico.MedicoModel;
import com.doctor.api.kauan_doctor.model.paciente.PacienteModel;
import com.doctor.api.kauan_doctor.repository.AgendaRepository;
import com.doctor.api.kauan_doctor.repository.ConsultaRepository;
import com.doctor.api.kauan_doctor.repository.MedicoRepository;
import com.doctor.api.kauan_doctor.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    // Conversor: DTO to Entity
    private ConsultaModel dtoEntity(ConsultaRequestDTO dto) {
        ConsultaModel consulta = new ConsultaModel();

        // GET Médico
        Optional<MedicoModel> medicoBuscado = medicoRepository.findById(dto.medico_id());
        medicoBuscado.ifPresent(consulta::setMedico);

        // GET Paciente
        Optional<PacienteModel> pacienteBuscado = pacienteRepository.findById(dto.paciente_id());
        pacienteBuscado.ifPresent(consulta::setPaciente);

        // GET Agenda
        Optional<AgendaModel> agendaBuscado = agendaRepository.findById(dto.agenda_id());
        agendaBuscado.ifPresent(consulta::setAgenda);

        // OS demais atributos
        consulta.setObservacao(dto.observacao());
        consulta.setStatus(dto.status());

        return consulta;
    }

    // Conversor: Entity to DTO
    private ConsultaResponseDTO entityToDTO(ConsultaModel consultaModel) {
        return new ConsultaResponseDTO(
                consultaModel.getId(),
                consultaModel.getMedico().getId(),
                consultaModel.getPaciente().getId(),
                consultaModel.getAgenda().getId(),
                consultaModel.getObservacao(),
                consultaModel.getStatus()
        );
    }

    // CREATE Consulta
    public ResponseEntity<ConsultaResponseDTO> createConsulta(ConsultaRequestDTO dto) {
        // GET Médico
        Optional<MedicoModel> medicoBuscado = medicoRepository.findById(dto.medico_id());
        // GET Paciente
        Optional<PacienteModel> pacienteBuscado = pacienteRepository.findById(dto.paciente_id());
        // GET Agenda
        Optional<AgendaModel> agendaBuscado = agendaRepository.findById(dto.agenda_id());

        if (medicoBuscado.isEmpty() || pacienteBuscado.isEmpty() || agendaBuscado.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        // ALTERANDO O STATUS DA AGENDA PARA RESERVADA
        AgendaModel agendaAlterada = agendaRepository.updateStatusAgenda(agendaBuscado.get().getId());

        // Setando os valores
        ConsultaModel consultaModel = dtoEntity(dto);
        consultaModel.setMedico(medicoBuscado.get());
        consultaModel.setPaciente(pacienteBuscado.get());
        // Colocando a agenda alterada
        consultaModel.setAgenda(agendaAlterada);
        consultaModel.setObservacao(dto.observacao());
        consultaModel.setStatus(dto.status());
        // Salvando no banco
        ConsultaModel consultaSalva = consultaRepository.save(consultaModel);
        return ResponseEntity.status(201).body(entityToDTO(consultaSalva));
    }

    // GET CONSULTAS TO A SPECIFIC MEDICO_ID
    public ResponseEntity<List<ConsultaResponseDTO>> listConsultasSpecificMedicoId(Long medico_id) {
        // BUSCANDO SE O MÉDICO REALMENTE EXISTE
        Optional<MedicoModel> medicoBuscado = medicoRepository.findById(medico_id);

        // SE O MÉDICO EXISTIR, PODE FAZER A OPERAÇÃO
        if (medicoBuscado.isPresent()) {
            // TRAZENDO A LISTA DE CONSULTAS DE UM MÉDICO E JÁ FAZENDO A CONVERSÃO PARA DTO
            List<ConsultaResponseDTO> listaConsultasDeUmMedico = consultaRepository.listaConsultasDeUmMedico(medico_id).stream()
                    .map(this::entityToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.status(200).body(listaConsultasDeUmMedico);
        }
        // RETORNO NEGATIVO CASO NÃO ENCONTRE ALGUM MÉDICO
        return ResponseEntity.status(404).build();
    }

    // GET HISTÓRICO DE CONSULTAS DE UM PACIENTE POR STATUS
    public ResponseEntity<List<ConsultaResponseDTO>> listaHistoricoConsultasPacientePorStatus(Long paciente_id, StatusConsultaEnum statusConsulta) {
        // BUSCANDO SE O PACIENTE REALMENTE EXISTE
        Optional<PacienteModel> pacienteBuscado = pacienteRepository.findById(paciente_id);

        // SE O PACIENTE EXISTIR, PODE FAZER A OPERAÇÃO
        if (pacienteBuscado.isPresent()) {
            // TRAZENDO A LISTA DE CONSULTAS DE UM PACIENTE JÁ FILTRADA POR UM STATUS E CONVERTANDO PARA DTO
            List<ConsultaResponseDTO> listaHistoricoConsultasPacientePorStatus = consultaRepository.consultasPacientePorStatus(paciente_id, statusConsulta).stream()
                    .map(this::entityToDTO)
                    .toList();
            return ResponseEntity.status(200).body(listaHistoricoConsultasPacientePorStatus);
        }
        // RETORNO NEGATIVO CASO NÃO ENCONTRE ALGUM PACIENTE
        return ResponseEntity.status(404).build();
    }

    // GET ALL CONSULTAS
    public ResponseEntity<List<ConsultaResponseDTO>> getAllConsultas() {
        List<ConsultaResponseDTO> consultas = consultaRepository.findAll()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.status(200).body(consultas);
    }

    // GET CONSULTA BY ID
    public ResponseEntity<ConsultaResponseDTO> getConsultaById(Long id) {
        Optional<ConsultaModel> consultaBuscado = consultaRepository.findById(id);
        return consultaBuscado
                .map(item -> ResponseEntity.status(200).body(entityToDTO(item)))
                .orElse(ResponseEntity.status(404).build());
    }

    // UPDATE CONSULTA
    public ResponseEntity<ConsultaResponseDTO> updateConsulta(Long id, ConsultaRequestDTO dto) {

        // VERIFICANDO SE A CONSULTA EXISTE
        Optional<ConsultaModel> consultaBuscado = consultaRepository.findById(id);

        // SE REALMENTE EXISTIR, FAÇA A OPERAÇÃO
        if (consultaBuscado.isPresent()) {
            ConsultaModel consultaModel = consultaBuscado.get();
            consultaModel.setMedico(medicoRepository.findById(dto.medico_id()).get());
            consultaModel.setAgenda(agendaRepository.findById(dto.agenda_id()).get());
            consultaModel.setObservacao(dto.observacao());
            consultaModel.setStatus(dto.status());
            consultaRepository.save(consultaModel);
            return ResponseEntity.status(201).body(entityToDTO(consultaModel));
        }

        // CASO NÃO EXISTA A CONSULTA
        return ResponseEntity.status(404).build();
    }

    // DELETE CONSULTA
    public ResponseEntity<ConsultaResponseDTO> deleteConsulta(Long id) {
        if (consultaRepository.existsById(id)) {
            consultaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        // CASO A CONSULTA NÃO EXISTA
        return ResponseEntity.status(404).build();
    }
}