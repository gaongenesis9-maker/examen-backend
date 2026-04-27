package com.example.examen.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.examen.Objeto.Consulta;
import com.example.examen.Servicio.ConsultaServicio;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaControlador {

    @Autowired
    private ConsultaServicio servicio;

    @GetMapping
    public List<Consulta> listar() {
        return servicio.listar();
    }

    @GetMapping("/mis-consultas")
    public List<Consulta> misConsultas(Authentication authentication) {
        String username = authentication.getName();
        return servicio.listarPorRol(username);
    }

    @GetMapping("/medico/{id}")
    public List<Consulta> listarPorMedico(@PathVariable Long id) {
        return servicio.listarPorMedico(id);
    }

    @GetMapping("/paciente/{id}")
    public List<Consulta> listarPorPaciente(@PathVariable Long id) {
        return servicio.listarPorPaciente(id);
    }

    @PostMapping
    public Consulta guardar(@Valid @RequestBody Consulta c) {
        return servicio.guardar(c);
    }

    @PostMapping("/mis-consultas")
    public Consulta guardarMiConsulta(@Valid @RequestBody Consulta c, Authentication authentication) {
        String username = authentication.getName();
        return servicio.guardarSegunRol(c, username);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
    }
}