package com.example.examen.Servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.examen.Objeto.Usuario;
import com.example.examen.Repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio repositorio;

    public Usuario guardar(Usuario u) {
        return repositorio.save(u);
    }

    public Usuario buscarPorUsername(String username) {
        return repositorio.findByUsername(username).orElse(null);
    }

    public List<Usuario> listar() {
        return repositorio.findAll();
    }

    public Usuario actualizar(Long id, Usuario u) {
        Usuario existente = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        existente.setUsername(u.getUsername());
        existente.setPassword(u.getPassword());
        existente.setRol(u.getRol());

        return repositorio.save(existente);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
}