package com.example.examen.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.examen.Objeto.Usuario;
import com.example.examen.Servicio.UsuarioServicio;
import com.example.examen.Seguridad.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthControlador {

    @Autowired
    private UsuarioServicio servicio;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Usuario registrar(@RequestBody Usuario u) {

        if (u.getRol() == null || u.getRol().isBlank()) {
            u.setRol("PACIENTE");
        }

        return servicio.guardar(u);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario u) {

        Usuario usuario = servicio.buscarPorUsername(u.getUsername());

        if (usuario != null && usuario.getPassword().equals(u.getPassword())) {

            if (usuario.getRol() == null || usuario.getRol().isBlank()) {
                usuario.setRol("PACIENTE");
                servicio.guardar(usuario);
            }

            String token = jwtUtil.generarToken(usuario.getUsername());

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("token", token);
            respuesta.put("username", usuario.getUsername());
            respuesta.put("rol", usuario.getRol());

            return ResponseEntity.ok(respuesta);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Credenciales incorrectas"));
    }
}