package com.example.examen.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.examen.Objeto.Historial;
import com.example.examen.Objeto.Medico;
import com.example.examen.Objeto.Paciente;
import com.example.examen.Objeto.Usuario;
import com.example.examen.Repositorio.HistorialRepositorio;
import com.example.examen.Repositorio.MedicoRepositorio;
import com.example.examen.Repositorio.PacienteRepositorio;
import com.example.examen.Repositorio.UsuarioRepositorio;

@Service
public class HistorialServicio {

    @Autowired
    private HistorialRepositorio repositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Historial guardar(Historial h) {
        validarHistorialBase(h, true, true);

        h.setPaciente(
            pacienteRepositorio.findById(h.getPaciente().getId())
                .orElseThrow(() -> new RuntimeException("Paciente no existe"))
        );

        h.setMedico(
            medicoRepositorio.findById(h.getMedico().getId())
                .orElseThrow(() -> new RuntimeException("Médico no existe"))
        );

        return repositorio.save(h);
    }

    public Historial guardarSegunRol(Historial h, String username) {
        Usuario usuario = usuarioRepositorio.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String rol = usuario.getRol();

        if ("ADMIN".equalsIgnoreCase(rol)) {
            return guardar(h);
        }

        if ("MEDICO".equalsIgnoreCase(rol)) {
            validarHistorialBase(h, true, false);

            Medico medico = medicoRepositorio.findByUsuarioUsername(username)
                    .orElseThrow(() -> new RuntimeException("Médico no encontrado para este usuario"));

            Paciente paciente = pacienteRepositorio.findById(h.getPaciente().getId())
                    .orElseThrow(() -> new RuntimeException("Paciente no existe"));

            h.setPaciente(paciente);
            h.setMedico(medico);

            return repositorio.save(h);
        }

        throw new RuntimeException("Solo ADMIN o MEDICO pueden registrar historial");
    }

    public List<Historial> listar() {
        return repositorio.findAll();
    }

    public List<Historial> listarPorRol(String username) {
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

    public List<Historial> listarPorPaciente(Long pacienteId) {
        return repositorio.findByPacienteId(pacienteId);
    }

    public List<Historial> listarPorMedico(Long medicoId) {
        return repositorio.findByMedicoId(medicoId);
    }

    public void eliminar(Long id) {
        Historial historial = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));

        repositorio.delete(historial);
    }

    private void validarHistorialBase(Historial h, boolean requierePaciente, boolean requiereMedico) {
        if (h == null) {
            throw new RuntimeException("El historial no puede ser nulo");
        }

        if (h.getFecha() == null) {
            throw new RuntimeException("La fecha es obligatoria");
        }

        if (h.getDiagnostico() == null || h.getDiagnostico().trim().isEmpty()) {
            throw new RuntimeException("El diagnóstico es obligatorio");
        }

        if (h.getObservacion() == null || h.getObservacion().trim().isEmpty()) {
            throw new RuntimeException("La observación es obligatoria");
        }

        if (requierePaciente) {
            if (h.getPaciente() == null || h.getPaciente().getId() == null) {
                throw new RuntimeException("Debe seleccionar un paciente");
            }
        }

        if (requiereMedico) {
            if (h.getMedico() == null || h.getMedico().getId() == null) {
                throw new RuntimeException("Debe seleccionar un médico");
            }
        }
    }
}