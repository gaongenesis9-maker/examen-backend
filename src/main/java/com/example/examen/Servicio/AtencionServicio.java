package com.example.examen.Servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.examen.Objeto.Atencion;
import com.example.examen.Objeto.Medico;
import com.example.examen.Objeto.Paciente;
import com.example.examen.Objeto.Usuario;
import com.example.examen.Repositorio.AtencionRepositorio;
import com.example.examen.Repositorio.MedicoRepositorio;
import com.example.examen.Repositorio.PacienteRepositorio;
import com.example.examen.Repositorio.UsuarioRepositorio;

@Service
public class AtencionServicio {

    @Autowired
    private AtencionRepositorio repositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Atencion guardar(Atencion a) {
        validarAtencionBase(a, true, true);

        a.setPaciente(
            pacienteRepositorio.findById(a.getPaciente().getId())
                .orElseThrow(() -> new RuntimeException("Paciente no existe"))
        );

        a.setMedico(
            medicoRepositorio.findById(a.getMedico().getId())
                .orElseThrow(() -> new RuntimeException("Médico no existe"))
        );

        return repositorio.save(a);
    }

    public Atencion guardarSegunRol(Atencion a, String username) {
        Usuario usuario = usuarioRepositorio.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String rol = usuario.getRol();

        if ("ADMIN".equalsIgnoreCase(rol)) {
            return guardar(a);
        }

        if ("MEDICO".equalsIgnoreCase(rol)) {
            validarAtencionBase(a, true, false);

            Medico medico = medicoRepositorio.findByUsuarioUsername(username)
                    .orElseThrow(() -> new RuntimeException("Médico no encontrado para este usuario"));

            Paciente paciente = pacienteRepositorio.findById(a.getPaciente().getId())
                    .orElseThrow(() -> new RuntimeException("Paciente no existe"));

            a.setPaciente(paciente);
            a.setMedico(medico);

            return repositorio.save(a);
        }

        throw new RuntimeException("Solo ADMIN o MEDICO pueden registrar atención");
    }

    public List<Atencion> listar() {
        return repositorio.findAll();
    }

    public List<Atencion> listarPorRol(String username) {
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

    public List<Atencion> listarPorPaciente(Long pacienteId) {
        return repositorio.findByPacienteId(pacienteId);
    }

    public List<Atencion> listarPorMedico(Long medicoId) {
        return repositorio.findByMedicoId(medicoId);
    }

    public void eliminar(Long id) {
        Atencion atencion = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Atención no encontrada"));

        repositorio.delete(atencion);
    }

    private void validarAtencionBase(Atencion a, boolean requierePaciente, boolean requiereMedico) {
        if (a == null) {
            throw new RuntimeException("La atención no puede ser nula");
        }

        if (a.getFecha() == null) {
            throw new RuntimeException("La fecha es obligatoria");
        }

        if (a.getIndicaciones() == null || a.getIndicaciones().trim().isEmpty()) {
            throw new RuntimeException("Las indicaciones son obligatorias");
        }

        if (requierePaciente) {
            if (a.getPaciente() == null || a.getPaciente().getId() == null) {
                throw new RuntimeException("Debe seleccionar un paciente");
            }
        }

        if (requiereMedico) {
            if (a.getMedico() == null || a.getMedico().getId() == null) {
                throw new RuntimeException("Debe seleccionar un médico");
            }
        }
    }
}