package cl.medipacs.compress;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sebastián Salazar Molina
 */
public class MediCompressUtils implements Serializable {

    private static final long serialVersionUID = 5800037075136892928L;
    private static Logger logger = LoggerFactory.getLogger(MediCompressUtils.class);

    public static Long crearLong(String valor) {
        Long numero = null;
        try {
            if (StringUtils.isNotBlank(valor)) {
                if (StringUtils.isNumeric(valor)) {
                    numero = Long.parseLong(valor);
                }
            }
        } catch (Exception e) {
            numero = null;
            logger.error("No fue posible convertir numero: {}", e.toString());
        }
        return numero;
    }
}
