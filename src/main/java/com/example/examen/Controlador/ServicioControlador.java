package com.example.examen.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.examen.Objeto.Servicio;
import com.example.examen.Servicio.ServicioServicio;    

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicios")
public class ServicioControlador {

    @Autowired
    private ServicioServicio servicio;

    // ✅ LISTAR
    @GetMapping
    public List<Servicio> listar() {
        return servicio.listar();
    }

    // ✅ GUARDAR
    @PostMapping
    public Servicio guardar(@Valid @RequestBody Servicio s) {
        return servicio.guardar(s);
    }

    // ✅ ACTUALIZAR (PUT)
    @PutMapping("/{id}")
    public Servicio actualizar(@PathVariable Long id, @RequestBody Servicio s) {
        return servicio.actualizar(id, s);
    }

    // ✅ ELIMINAR
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
    }
}