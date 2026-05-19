package com.example.examen.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.examen.Objeto.Medico;
import com.example.examen.Repositorio.MedicoRepositorio;

@Service
public class MedicoServicio {

    @Autowired
    private MedicoRepositorio repositorio;

    public List<Medico> listar() {
        return repositorio.findAll();
    }

    public Medico guardar(Medico m) {
        return repositorio.save(m);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }

    public Medico actualizar(Long id, Medico mActualizado) {
        Medico medicoExistente = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con id: " + id));

        medicoExistente.setNombre(mActualizado.getNombre());
        medicoExistente.setEspecialidad(mActualizado.getEspecialidad());

        if (mActualizado.getUsuario() != null) {
            medicoExistente.setUsuario(mActualizado.getUsuario());
        }

        return repositorio.save(medicoExistente);
    }
}