package com.example.examen.Servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.examen.Objeto.Paciente;
import com.example.examen.Repositorio.PacienteRepositorio;

@Service
public class PacienteServicio {

    @Autowired
    private PacienteRepositorio repositorio;

    public List<Paciente> listar() {
        return repositorio.findAll();
    }

    public Paciente guardar(Paciente p) {
        return repositorio.save(p);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
    public Paciente actualizar(Long id, Paciente p) {
    p.setId(id);
    return repositorio.save(p);
    }
}