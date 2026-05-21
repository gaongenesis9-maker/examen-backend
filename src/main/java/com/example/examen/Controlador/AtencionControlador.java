package com.example.examen.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.examen.Objeto.Atencion;
import com.example.examen.Objeto.Paciente;
import com.example.examen.Servicio.AtencionServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/atenciones")
public class AtencionControlador {

    @Autowired
    private AtencionServicio servicio;

    @GetMapping
    public List<Atencion> listar() {
        return servicio.listar();
    }

    @GetMapping("/mis-atenciones")
    public List<Atencion> misAtenciones(Authentication authentication) {
        String username = authentication.getName();
        return servicio.listarPorRol(username);
    }

    @GetMapping("/mis-pacientes")
    public List<Paciente> misPacientes(Authentication authentication) {
        String username = authentication.getName();
        return servicio.obtenerPacientesDelMedico(username);
    }

    @GetMapping("/paciente/{id}")
    public List<Atencion> listarPorPaciente(@PathVariable Long id) {
        return servicio.listarPorPaciente(id);
    }

    @GetMapping("/medico/{id}")
    public List<Atencion> listarPorMedico(@PathVariable Long id) {
        return servicio.listarPorMedico(id);
    }

    @PostMapping
    public Atencion guardar(@Valid @RequestBody Atencion a) {
        return servicio.guardar(a);
    }

    @PostMapping("/mis-atenciones")
    public Atencion guardarMiAtencion(@Valid @RequestBody Atencion a, Authentication authentication) {
        String username = authentication.getName();
        return servicio.guardarSegunRol(a, username);
    }

    @PutMapping("/{id}")
    public Atencion actualizar(@PathVariable Long id, @Valid @RequestBody Atencion a) {
        return servicio.actualizar(id, a);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
    }
}