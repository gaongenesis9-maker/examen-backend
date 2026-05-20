package com.example.examen.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.examen.Objeto.Historial;

public interface HistorialRepositorio extends JpaRepository<Historial, Long> {

    List<Historial> findByPacienteId(Long pacienteId);

    List<Historial> findByMedicoId(Long medicoId);

    List<Historial> findByMedicoUsuarioUsername(String username);

    List<Historial> findByPacienteUsuarioUsername(String username);
}