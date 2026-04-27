package com.example.examen.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.examen.Objeto.Consulta;
import com.example.examen.Objeto.Paciente;
import com.example.examen.Objeto.Servicio;
import com.example.examen.Objeto.Usuario;
import com.example.examen.Repositorio.ConsultaRepositorio;
import com.example.examen.Repositorio.MedicoRepositorio;
import com.example.examen.Repositorio.PacienteRepositorio;
import com.example.examen.Repositorio.ServicioRepositorio;
import com.example.examen.Repositorio.UsuarioRepositorio;

@Service
public class ConsultaServicio {

    @Autowired
    private ConsultaRepositorio repositorio;

    @Autowired
    private ServicioRepositorio servicioRepositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Consulta guardar(Consulta c) {

        c.setPaciente(
            pacienteRepositorio.findById(c.getPaciente().getId())
                .orElseThrow(() -> new RuntimeException("Paciente no existe"))
        );

        c.setMedico(
            medicoRepositorio.findById(c.getMedico().getId())
                .orElseThrow(() -> new RuntimeException("Médico no existe"))
        );

        List<Servicio> servicios = new java.util.ArrayList<>();

        for (Servicio s : c.getServicios()) {
            Servicio servicio = servicioRepositorio.findById(s.getId())
                .orElseThrow(() -> new RuntimeException("Servicio no existe: " + s.getId()));

            servicios.add(servicio);
        }

        c.setServicios(servicios);

        return repositorio.save(c);
    }

    public Consulta guardarSegunRol(Consulta c, String username) {
        Usuario usuario = usuarioRepositorio.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String rol = usuario.getRol();

        if ("ADMIN".equalsIgnoreCase(rol)) {
            return guardar(c);
        }

        if ("PACIENTE".equalsIgnoreCase(rol)) {
            Paciente paciente = pacienteRepositorio.findByUsuarioUsername(username)
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado para este usuario"));

            c.setPaciente(paciente);

            c.setMedico(
                medicoRepositorio.findById(c.getMedico().getId())
                    .orElseThrow(() -> new RuntimeException("Médico no existe"))
            );

            List<Servicio> servicios = new java.util.ArrayList<>();

            for (Servicio s : c.getServicios()) {
                Servicio servicio = servicioRepositorio.findById(s.getId())
                    .orElseThrow(() -> new RuntimeException("Servicio no existe: " + s.getId()));
                servicios.add(servicio);
            }

            c.setServicios(servicios);

            return repositorio.save(c);
        }

        throw new RuntimeException("Solo ADMIN o PACIENTE pueden registrar consultas");
    }

    public List<Consulta> listar() {
        return repositorio.findAll();
    }

    public List<Consulta> listarPorRol(String username) {
        Usuario usuario = usuarioRepositorio.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String rol = usuario.getRol();

        if ("ADMIN".equalsIgnoreCase(rol)) {
            return repositorio.findAll();
        }

        if ("MEDICO".equalsIgnoreCase(rol)) {
            return repositorio.findByMedicoUsuarioUsername(username);
        }

        if ("PACIENTE".equalsIgnoreCase(rol)) {
            return repositorio.findByPacienteUsuarioUsername(username);
        }

        throw new RuntimeException("Rol no válido");
    }

    public List<Consulta> listarPorMedico(Long medicoId) {
        return repositorio.findByMedicoId(medicoId);
    }

    public List<Consulta> listarPorPaciente(Long pacienteId) {
        return repositorio.findByPacienteId(pacienteId);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
}