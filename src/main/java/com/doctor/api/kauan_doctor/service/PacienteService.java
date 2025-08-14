package com.doctor.api.kauan_doctor.service;

import com.doctor.api.kauan_doctor.model.paciente.PacienteModel;
import com.doctor.api.kauan_doctor.model.paciente.PacienteRequestDTO;
import com.doctor.api.kauan_doctor.model.paciente.PacienteResponseDTO;
import com.doctor.api.kauan_doctor.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Conversor: DTO to Entity
    private PacienteModel dtoToEntity(PacienteRequestDTO dto){
        PacienteModel pacienteModel = new PacienteModel();
        pacienteModel.setNome(dto.nome());
        pacienteModel.setEmail(dto.email());
        pacienteModel.setSenha(dto.senha());
        return pacienteModel;
    }

    // Conversor Entity to DTO
    private PacienteResponseDTO entityToDto(PacienteModel pacienteModel){
        return new PacienteResponseDTO(
                pacienteModel.getId(),
                pacienteModel.getNome(),
                pacienteModel.getEmail(),
                pacienteModel.getRole()
        );
    }

    // CREATE PACIENTE
    public ResponseEntity<PacienteResponseDTO> createPaciente(PacienteRequestDTO dto){
        PacienteModel paciente = dtoToEntity(dto);
        paciente.setSenha(passwordEncoder.encode(dto.senha()));
        PacienteModel pacienteSalvo = pacienteRepository.save(paciente);
        return ResponseEntity.status(201).body(entityToDto(pacienteSalvo));
    }

    // GET ALL PACIENTE
    public ResponseEntity<List<PacienteResponseDTO>> getAllPacientes(){
        // BUSCA DE TODOS OS PACIENTES
        List<PacienteResponseDTO> pacientes = pacienteRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(200).body(pacientes);
    }

    // GET PACIENTE BY ID
    public ResponseEntity<PacienteResponseDTO> getPacienteById(Long id){
        // BUSCANDO UM PACIENTE EM ESPECIFICO
        Optional<PacienteModel> paciente = pacienteRepository.findById(id);
        return paciente
                .map(item -> ResponseEntity.status(200).body(entityToDto(item)))
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE USER
    public ResponseEntity<PacienteResponseDTO> updatePaciente(Long id, PacienteRequestDTO dto){
        // VERIFICANDO SE O PACIENTE EXISTE
        Optional<PacienteModel> paciente = pacienteRepository.findById(id);

        // SE REALMENTE EXISTIR
        if(paciente.isPresent()){
            PacienteModel pacienteModel = paciente.get();
            pacienteModel.setNome(dto.nome());
            pacienteModel.setEmail(dto.email());

            // SE VIER ALGUMA SENHA, FAÇA O UPDATE E CRIPTOGRAFA
            if (dto.senha() != null && !dto.senha().isEmpty()) {
                pacienteModel.setSenha(passwordEncoder.encode(dto.senha()));
            }

            // SAVE IN DATABASE
            PacienteModel pacienteSalvo = pacienteRepository.save(pacienteModel);
            return ResponseEntity.status(200).body(entityToDto(pacienteSalvo));
        }

        // CASO NÃO VENHA UM PACIENTE VÁLIDO
        return ResponseEntity.notFound().build();
    }

    // DELETAR PACIENTE
    public ResponseEntity<PacienteResponseDTO> deletePaciente(Long id){
        // VERIFICANDO SE O PACIENTE EXISTE
        if(pacienteRepository.existsById(id)){
            pacienteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        // CASO O PACIENTE NÃO EXISTIR NA BASE DE DADOS
        return ResponseEntity.notFound().build();
    }
}