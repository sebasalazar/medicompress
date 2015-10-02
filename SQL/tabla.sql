BEGIN TRANSACTION;

DROP TABLE IF EXISTS compresiones CASCADE;
CREATE TABLE compresiones (
    id bigserial NOT NULL,
    fecha timestamp NOT NULL DEFAULT NOW(),
    paciente_id bigint DEFAULT '0',
    archivo varchar(255) NOT NULL,
    cantidad_examenes int NOT NULL DEFAULT '0',
    tiempo_procesamiento numeric NOT NULL DEFAULT '0',
    codigo_salida int DEFAULT '-1',
    PRIMARY KEY (id)
);

COMMIT;
