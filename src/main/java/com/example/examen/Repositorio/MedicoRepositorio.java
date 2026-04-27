package com.example.examen.Repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.examen.Objeto.Medico;

public interface MedicoRepositorio extends JpaRepository<Medico, Long> {
    Optional<Medico> findByUsuarioUsername(String username);
}