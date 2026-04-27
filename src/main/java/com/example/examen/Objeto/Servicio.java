package com.example.examen.Objeto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;

    @Positive
    private double costo;

    @ManyToMany(mappedBy = "servicios")
    @JsonIgnore
    private List<Consulta> consultas;

    public void setId(Long id) {
        this.id = id;
    }
}