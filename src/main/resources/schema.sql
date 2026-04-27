DROP TABLE IF EXISTS consulta_servicio;

CREATE TABLE consulta_servicio (
  consulta_id BIGINT NOT NULL,
  servicio_id BIGINT NOT NULL,
  PRIMARY KEY (consulta_id, servicio_id),
  CONSTRAINT fk_consulta_servicio_consulta
    FOREIGN KEY (consulta_id) REFERENCES consultas(id)
    ON DELETE CASCADE,
  CONSTRAINT fk_consulta_servicio_servicio
    FOREIGN KEY (servicio_id) REFERENCES servicios(id)
    ON DELETE CASCADE
);