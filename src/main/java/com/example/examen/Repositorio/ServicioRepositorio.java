package com.example.examen.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.examen.Objeto.Servicio;

public interface ServicioRepositorio extends JpaRepository<Servicio, Long> {
}