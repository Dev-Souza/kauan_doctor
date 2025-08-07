package com.doctor.api.kauan_doctor.service;

import com.doctor.api.kauan_doctor.model.paciente.PacienteModel;
import com.doctor.api.kauan_doctor.model.paciente.PacienteRequestDTO;
import com.doctor.api.kauan_doctor.model.paciente.PacienteResponseDTO;
import com.doctor.api.kauan_doctor.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}