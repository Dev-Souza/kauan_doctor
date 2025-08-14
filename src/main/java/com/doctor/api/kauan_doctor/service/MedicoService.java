package com.doctor.api.kauan_doctor.service;

import com.doctor.api.kauan_doctor.model.medico.MedicoModel;
import com.doctor.api.kauan_doctor.model.medico.MedicoRequestDTO;
import com.doctor.api.kauan_doctor.model.medico.MedicoResponseDTO;
import com.doctor.api.kauan_doctor.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // GET ALL MÉDICOS
    public ResponseEntity<List<MedicoResponseDTO>> getAllMedicos(){
        List<MedicoResponseDTO> medicos = medicoRepository.findAll()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.status(200).body(medicos);
    }

    // GET BY ID MEDICO
    public ResponseEntity<MedicoResponseDTO> getMedicoById(Long id){
        // VERIFICANDO SE O MEDICO EXISTE
        Optional<MedicoModel> medico = medicoRepository.findById(id);

        // CASO O MÉDICO EXISTA, FAÇA A OPERAÇÃO
        return medico
                .map(med -> ResponseEntity.status(200).body(entityToDTO(med)))
                .orElse(ResponseEntity.status(404).build());
    }

    // UPDATE MÉDICO
    public ResponseEntity<MedicoResponseDTO> updateMedico(Long id, MedicoRequestDTO dto){
        // VERFICANDO SE O MÉDICO EXISTE
        Optional<MedicoModel> medico = medicoRepository.findById(id);

        // CASO EXISTA, FAÇA A OPERAÇÃO
        if(medico.isPresent()){
            MedicoModel medicoModel = medico.get();
            medicoModel.setNome(dto.nome());
            medicoModel.setCrm(dto.crm());
            medicoModel.setEmail(dto.email());

            // CASO O MÉDICO QUEIRA TROCAR DE SENHA
            if(dto.senha() != null && !dto.senha().isEmpty()){
                medicoModel.setSenha(passwordEncoder.encode(dto.senha()));
            }

            // SAVE IN DATABASE
            MedicoModel medicoSalvo = medicoRepository.save(medicoModel);
            return ResponseEntity.status(200).body(entityToDTO(medicoSalvo));
        }

        // CASO NÃO EXISTA O MÉDICO
        return ResponseEntity.status(404).build();
    }

    // DELETE MÉDICO
    public ResponseEntity<MedicoResponseDTO> deleteMedico(Long id){
        if(medicoRepository.existsById(id)){
            medicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(404).build();
    }
}
