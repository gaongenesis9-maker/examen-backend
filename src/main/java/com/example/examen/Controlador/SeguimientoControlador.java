package com.example.examen.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.examen.Objeto.Seguimiento;
import com.example.examen.Servicio.SeguimientoServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/seguimientos")
public class SeguimientoControlador {

    @Autowired
    private SeguimientoServicio servicio;

    @GetMapping
    public List<Seguimiento> listar() {
        return servicio.listar();
    }

    @GetMapping("/mis-seguimientos")
    public List<Seguimiento> misSeguimientos(Authentication authentication) {
        String username = authentication.getName();
        return servicio.listarPorRol(username);
    }

    @GetMapping("/paciente/{id}")
    public List<Seguimiento> listarPorPaciente(@PathVariable Long id) {
        return servicio.listarPorPaciente(id);
    }

    @GetMapping("/medico/{id}")
    public List<Seguimiento> listarPorMedico(@PathVariable Long id) {
        return servicio.listarPorMedico(id);
    }

    @PostMapping
    public Seguimiento guardar(@Valid @RequestBody Seguimiento s) {
        return servicio.guardar(s);
    }

    @PostMapping("/mis-seguimientos")
    public Seguimiento guardarMiSeguimiento(@Valid @RequestBody Seguimiento s, Authentication authentication) {
        String username = authentication.getName();
        return servicio.guardarSegunRol(s, username);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
    }
}