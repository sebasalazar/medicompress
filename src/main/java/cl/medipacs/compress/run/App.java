package cl.medipacs.compress.run;

import cl.medipacs.compress.modelo.Compresion;
import cl.medipacs.compress.servicio.ServicioDB;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@experti.cl>
 */
public class App implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Date ahora = new Date();
            long tiempoInicio = ahora.getTime();

            ServicioDB servicio = new ServicioDB();

            Long id = Long.parseLong(args[0]);
            String nombre = StringUtils.trimToEmpty(args[1]);

            List<String> consultarExamenes = servicio.consultarExamenes(id, nombre);
            StringBuilder sb = new StringBuilder();
            for (String exm : consultarExamenes) {
                sb.append(String.format("%s ", exm));
            }

            String nombreArchivo = String.format("/srv/web/medipacs.cl/www/htdocs/zip/%s%d.zip", nombre, id);
            File zip = new File(nombreArchivo);

            int codigo = -1;
            if (!zip.isFile()) {
                String linea = String.format("/usr/bin/zip -5 %s %s", nombreArchivo, StringUtils.trimToEmpty(sb.toString()));
                logger.debug(linea);

                Process p = Runtime.getRuntime().exec(linea);
                p.waitFor();
                codigo = p.exitValue();
            }

            // Objeto Compresión, unica finalidad registro de examenes
            long tiempoFin = new Date().getTime();
            Compresion compresion = new Compresion();
            compresion.setArchivo(nombreArchivo);
            compresion.setCantidadExamenes(consultarExamenes.size());
            compresion.setFecha(ahora);
            compresion.setPacienteId(id);
            compresion.setCodigoSalida(codigo);
            compresion.setTiempoProcesamiento(new BigDecimal((tiempoFin - tiempoInicio) / 1000.0));
            boolean okGuardado = servicio.guardar(compresion);

            logger.info("Archivo: '{}' # ok: '{}' # db: '{}'", nombreArchivo, zip.isFile(), okGuardado);
        } catch (Exception e) {
            String mensaje = String.format("Error al procesar: %s", e.toString());
            logger.error(mensaje);
            logger.debug(mensaje, e);
            System.out.println(mensaje);
        }
    }

}
