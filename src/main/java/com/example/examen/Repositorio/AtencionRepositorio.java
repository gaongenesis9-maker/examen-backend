package com.example.examen.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.examen.Objeto.Atencion;

public interface AtencionRepositorio extends JpaRepository<Atencion, Long> {

    List<Atencion> findByPacienteId(Long pacienteId);

    List<Atencion> findByMedicoId(Long medicoId);

    List<Atencion> findByMedicoUsuarioUsername(String username);

    List<Atencion> findByPacienteUsuarioUsername(String username);
}