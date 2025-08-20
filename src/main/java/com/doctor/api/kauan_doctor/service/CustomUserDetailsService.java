package com.doctor.api.kauan_doctor.service;

import com.doctor.api.kauan_doctor.infra.security.UserSecurity;
import com.doctor.api.kauan_doctor.model.medico.MedicoModel;
import com.doctor.api.kauan_doctor.model.paciente.PacienteModel;
import com.doctor.api.kauan_doctor.repository.MedicoRepository;
import com.doctor.api.kauan_doctor.repository.PacienteRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public CustomUserDetailsService(MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Tenta encontrar um médico pelo email
        MedicoModel medico = medicoRepository.findByEmail(email).orElse(null);
        if (medico != null) {
            return new UserSecurity(
                    medico.getId(),
                    medico.getEmail(),
                    medico.getSenha(),
                    medico.getRole().name()
            );
        }

        // Tenta encontrar um paciente pelo email
        PacienteModel paciente = pacienteRepository.findByEmail(email).orElse(null);
        if (paciente != null) {
            return new UserSecurity(
                    paciente.getId(),
                    paciente.getEmail(),
                    paciente.getSenha(),
                    paciente.getRole().name()
            );
        }

        // Se não encontrar nenhum
        throw new UsernameNotFoundException("Usuário não encontrado com email: " + email);
    }
}