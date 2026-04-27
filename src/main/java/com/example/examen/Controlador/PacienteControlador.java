package com.example.examen.Controlador;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.examen.Objeto.Paciente;
import com.example.examen.Servicio.PacienteServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteControlador {

    @Autowired
    private PacienteServicio service;

    @GetMapping
    public List<Paciente> listar() {
        return service.listar();
    }

    @PostMapping
    public Paciente guardar(@Valid @RequestBody Paciente p) {
        return service.guardar(p);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PutMapping("/{id}")
    public Paciente actualizar(@PathVariable Long id, @RequestBody Paciente p) {
         return service.actualizar(id, p);
    }
}