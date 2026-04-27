package com.example.examen.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.examen.Objeto.Servicio;
import com.example.examen.Repositorio.ServicioRepositorio;

@Service
public class ServicioServicio {

    @Autowired
    private ServicioRepositorio repositorio;

    public List<Servicio> listar() {
        return repositorio.findAll();
    }

    public Servicio guardar(Servicio s) {
        return repositorio.save(s);
    }

    public Servicio actualizar(Long id, Servicio s) {
        Servicio existente = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no existe"));

        existente.setNombre(s.getNombre());
        existente.setDescripcion(s.getDescripcion());
        existente.setCosto(s.getCosto());

        return repositorio.save(existente);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
}