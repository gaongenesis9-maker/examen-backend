package com.example.examen.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.examen.Objeto.Usuario;
import com.example.examen.Servicio.UsuarioServicio;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio servicio;

    @GetMapping
    public List<Usuario> listar() {
        return servicio.listar();
    }

    @PostMapping
    public Usuario guardar(@RequestBody Usuario u) {
        return servicio.guardar(u);
    }

    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id, @RequestBody Usuario u) {
        return servicio.actualizar(id, u);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
    }
}