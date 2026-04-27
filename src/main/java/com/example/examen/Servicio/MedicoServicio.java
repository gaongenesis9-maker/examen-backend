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

    public Medico actualizar(Long id, Medico m) {
        m.setId(id);
        return repositorio.save(m);
    }
}