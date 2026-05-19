package com.example.examen.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.examen.Objeto.Medico;
import com.example.examen.Servicio.MedicoServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicos")
public class MedicoControlador {

    @Autowired
    private MedicoServicio servicio;

    @GetMapping
    public List<Medico> listar() {
        return servicio.listar();
    }

    @PostMapping
    public Medico guardar(@Valid @RequestBody Medico m) {
        return servicio.guardar(m);
    }

    @PutMapping("/{id}")
    public Medico actualizar(@PathVariable Long id, @Valid @RequestBody Medico m) {
        return servicio.actualizar(id, m);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
    }
}