package com.example.examen.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.examen.Objeto.Medico;
import com.example.examen.Objeto.Paciente;
import com.example.examen.Objeto.Seguimiento;
import com.example.examen.Objeto.Usuario;
import com.example.examen.Repositorio.MedicoRepositorio;
import com.example.examen.Repositorio.PacienteRepositorio;
import com.example.examen.Repositorio.SeguimientoRepositorio;
import com.example.examen.Repositorio.UsuarioRepositorio;

@Service
public class SeguimientoServicio {

    @Autowired
    private SeguimientoRepositorio repositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Seguimiento guardar(Seguimiento s) {
        validarSeguimientoBase(s, true, true);

        s.setPaciente(
            pacienteRepositorio.findById(s.getPaciente().getId())
                .orElseThrow(() -> new RuntimeException("Paciente no existe"))
        );

        s.setMedico(
            medicoRepositorio.findById(s.getMedico().getId())
                .orElseThrow(() -> new RuntimeException("Médico no existe"))
        );

        return repositorio.save(s);
    }

    public Seguimiento guardarSegunRol(Seguimiento s, String username) {
        Usuario usuario = usuarioRepositorio.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String rol = usuario.getRol();

        if ("ADMIN".equalsIgnoreCase(rol)) {
            return guardar(s);
        }

        if ("MEDICO".equalsIgnoreCase(rol)) {
            validarSeguimientoBase(s, true, false);

            Medico medico = medicoRepositorio.findByUsuarioUsername(username)
                    .orElseThrow(() -> new RuntimeException("Médico no encontrado para este usuario"));

            Paciente paciente = pacienteRepositorio.findById(s.getPaciente().getId())
                    .orElseThrow(() -> new RuntimeException("Paciente no existe"));

            s.setPaciente(paciente);
            s.setMedico(medico);

            return repositorio.save(s);
        }

        throw new RuntimeException("Solo ADMIN o MEDICO pueden registrar seguimientos");
    }

    public List<Seguimiento> listar() {
        return repositorio.findAll();
    }

    public List<Seguimiento> listarPorRol(String username) {
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

    public List<Seguimiento> listarPorPaciente(Long pacienteId) {
        return repositorio.findByPacienteId(pacienteId);
    }

    public List<Seguimiento> listarPorMedico(Long medicoId) {
        return repositorio.findByMedicoId(medicoId);
    }

    public void eliminar(Long id) {
        Seguimiento seguimiento = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Seguimiento no encontrado"));

        repositorio.delete(seguimiento);
    }

    private void validarSeguimientoBase(Seguimiento s, boolean requierePaciente, boolean requiereMedico) {
        if (s == null) {
            throw new RuntimeException("El seguimiento no puede ser nulo");
        }

        if (s.getFecha() == null) {
            throw new RuntimeException("La fecha es obligatoria");
        }

        if (s.getEstado() == null || s.getEstado().trim().isEmpty()) {
            throw new RuntimeException("El estado es obligatorio");
        }

        if (s.getObservacion() == null || s.getObservacion().trim().isEmpty()) {
            throw new RuntimeException("La observación es obligatoria");
        }

        if (requierePaciente) {
            if (s.getPaciente() == null || s.getPaciente().getId() == null) {
                throw new RuntimeException("Debe seleccionar un paciente");
            }
        }

        if (requiereMedico) {
            if (s.getMedico() == null || s.getMedico().getId() == null) {
                throw new RuntimeException("Debe seleccionar un médico");
            }
        }
    }
}