package com.example.examen.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.examen.Objeto.Consulta;

public interface ConsultaRepositorio extends JpaRepository<Consulta, Long> {
    List<Consulta> findByPacienteUsuarioUsername(String username);
    List<Consulta> findByMedicoUsuarioUsername(String username);
    List<Consulta> findByMedicoId(Long medicoId);
    List<Consulta> findByPacienteId(Long pacienteId);
}