package com.doctor.api.kauan_doctor.service;

import com.doctor.api.kauan_doctor.model.medico.MedicoModel;
import com.doctor.api.kauan_doctor.model.medico.MedicoRequestDTO;
import com.doctor.api.kauan_doctor.model.medico.MedicoResponseDTO;
import com.doctor.api.kauan_doctor.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Conversor: DTO to Entity
    private MedicoModel dtoToEntity(MedicoRequestDTO dto){
        MedicoModel medico = new MedicoModel();
        medico.setNome(dto.nome());
        medico.setCrm(dto.crm());
        medico.setEmail(dto.email());
        medico.setSenha(dto.senha());

        return medico;
    }

    // Conversor: Entity to DTO
    private MedicoResponseDTO entityToDTO(MedicoModel medicoModel) {
        return new MedicoResponseDTO(
                medicoModel.getId(),
                medicoModel.getNome(),
                medicoModel.getEmail(),
                medicoModel.getRole()
        );
    }

    // CREATE MÉDICO
    public ResponseEntity<MedicoResponseDTO> createMedico(MedicoRequestDTO dto){
        MedicoModel medico = dtoToEntity(dto);
        medico.setSenha(passwordEncoder.encode(dto.senha()));
        MedicoModel medicoSalvo = medicoRepository.save(medico);
        return ResponseEntity.status(201).body(entityToDTO(medicoSalvo));
    }
}
