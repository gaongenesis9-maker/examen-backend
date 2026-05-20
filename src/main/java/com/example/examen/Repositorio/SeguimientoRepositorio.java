package com.example.examen.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.examen.Objeto.Seguimiento;

public interface SeguimientoRepositorio extends JpaRepository<Seguimiento, Long> {

    List<Seguimiento> findByPacienteId(Long pacienteId);

    List<Seguimiento> findByMedicoId(Long medicoId);

    List<Seguimiento> findByMedicoUsuarioUsername(String username);

    List<Seguimiento> findByPacienteUsuarioUsername(String username);
}