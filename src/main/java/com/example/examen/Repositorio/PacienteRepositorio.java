package com.example.examen.Repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.examen.Objeto.Paciente;

public interface PacienteRepositorio extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByUsuarioUsername(String username);
}