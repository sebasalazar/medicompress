package cl.medipacs.compress.run;

import cl.medipacs.compress.servicio.ServicioDB;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Sebastián Salazar Molina <ssalazar@experti.cl>
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ServicioDB servicio = new ServicioDB();

            Long id = Long.parseLong(args[0]);
            String nombre = StringUtils.trimToEmpty(args[1]);

            List<String> consultarExamenes = servicio.consultarExamenes(id, nombre);
            StringBuilder sb = new StringBuilder();
            for (String exm : consultarExamenes) {
                sb.append(String.format("%s ", exm));
            }
            String linea = String.format("/usr/bin/zip -5 /srv/web/medipacs.cl/www/htdocs/zip/%s.zip %s", nombre, StringUtils.trimToEmpty(sb.toString()));

            Process p = Runtime.getRuntime().exec(linea);
            p.waitFor();
        } catch (Exception e) {
            System.out.println(String.format("Error al procesar: %s", e.toString()));
        }
    }

}
