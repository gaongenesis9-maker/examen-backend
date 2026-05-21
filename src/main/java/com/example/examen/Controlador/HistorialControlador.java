package com.example.examen.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.examen.Objeto.Historial;
import com.example.examen.Servicio.HistorialServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/historiales")
public class HistorialControlador {

    @Autowired
    private HistorialServicio servicio;

    @GetMapping
    public List<Historial> listar() {
        return servicio.listar();
    }

    @GetMapping("/mis-historiales")
    public List<Historial> misHistoriales(Authentication authentication) {
        String username = authentication.getName();
        return servicio.listarPorRol(username);
    }

    @GetMapping("/paciente/{id}")
    public List<Historial> listarPorPaciente(@PathVariable Long id) {
        return servicio.listarPorPaciente(id);
    }

    @GetMapping("/medico/{id}")
    public List<Historial> listarPorMedico(@PathVariable Long id) {
        return servicio.listarPorMedico(id);
    }

    @PostMapping
    public Historial guardar(@Valid @RequestBody Historial h) {
        return servicio.guardar(h);
    }

    @PostMapping("/mis-historiales")
    public Historial guardarMiHistorial(@Valid @RequestBody Historial h, Authentication authentication) {
        String username = authentication.getName();
        return servicio.guardarSegunRol(h, username);
    }

    @PutMapping("/{id}")
    public Historial actualizar(@PathVariable Long id, @Valid @RequestBody Historial h) {
        return servicio.actualizar(id, h);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
    }
}