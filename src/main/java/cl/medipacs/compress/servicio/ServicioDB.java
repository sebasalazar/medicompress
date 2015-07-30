package cl.medipacs.compress.servicio;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@orangepeople.cl>
 */
public class ServicioDB implements Serializable {

    private boolean conectado = false;
    private Connection conexion = null;
    private static final Logger logger = LoggerFactory.getLogger(ServicioDB.class);

    @PostConstruct
    public void iniciar() {
        boolean ok = conectar();
        if (!ok) {
            logger.info("ERROR: no fue posible conectarme a la base de datos");
        }

    }

    boolean conectar() {
        this.conectado = false;
        try {
            String url = "jdbc:postgresql:pacsdb";
            Properties props = new Properties();
            props.setProperty("user", "pacs");
            props.setProperty("password", "pacs");
            this.conexion = DriverManager.getConnection(url, props);

            if (conexion != null) {
                this.conectado = true;
            } else {
                this.conectado = false;
            }

            if (!conectado) {
                throw new RuntimeException("No se puede conectar al motor de base de datos.");
            }

        } catch (Exception e) {
            this.conectado = false;
            logger.error("Error al conectar con Base de datos: {}", e.toString());
            logger.debug("Error al conectar con Base de datos: {}", e.toString(), e);
        }
        return conectado;
    }

    boolean desconectar() {
        try {
            if (conexion != null) {
                conexion.close();
                conexion = null;
                conectado = false;
            } else {
                conectado = false;
            }
        } catch (Exception e) {
            conexion = null;
            conectado = false;
            logger.error("Error al desconectar con Base de datos: {}", e.toString());
            logger.debug("Error al desconectar con Base de datos: {}", e.toString(), e);
        }
        return conectado;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    @PreDestroy
    public void finalizar() {
        boolean desconectar = desconectar();
        if (desconectar) {
            logger.info("ERROR: conexión aún activa");
        }
    }

    public List<String> consultarExamenes(Long id, String nombre) {
        List<String> examenes = new ArrayList<String>();
        try {
            if (id != null && StringUtils.isNotBlank(nombre)) {
                String sql = String.format("SELECT patient.pat_name AS paciente, files.filepath AS archivo FROM patient INNER JOIN study ON (patient.pk = study.patient_fk) INNER JOIN series ON (study.pk = series.study_fk) INNER JOIN instance ON (series.pk = instance.series_fk ) INNER JOIN files ON (instance.pk = files.instance_fk) INNER JOIN filesystem ON (files.filesystem_fk = filesystem.pk) WHERE (study.pk = '%d' ) ", id);
                PreparedStatement pst = conexion.prepareStatement(sql);
                if (pst != null) {
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        String paciente = rs.getString("paciente");
                        String archivo = rs.getString("archivo");
                        
                        File archivoTemporal = new File(archivo);
                        if (archivoTemporal != null) {
                            if (archivoTemporal.isFile()) {
                                examenes.add(archivo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            examenes = new ArrayList<String>();
            logger.error("Error al obtener archivos: {}", e.toString());
            logger.debug("Error al obtener archivos: {}", e.toString(), e);
        }
        return examenes;
    }
}
