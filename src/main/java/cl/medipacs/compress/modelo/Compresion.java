package cl.medipacs.compress.modelo;

import java.math.BigDecimal;
import java.util.Date;

public class Compresion {

    private Long id = null;
    private Date fecha = new Date();
    private Long pacienteId = null;
    private String archivo = null;
    private Integer cantidadExamenes = 0;
    private BigDecimal tiempoProcesamiento = BigDecimal.ZERO;
    private Integer codigoSalida = -1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public Integer getCantidadExamenes() {
        return cantidadExamenes;
    }

    public void setCantidadExamenes(Integer cantidadExamenes) {
        this.cantidadExamenes = cantidadExamenes;
    }

    public BigDecimal getTiempoProcesamiento() {
        return tiempoProcesamiento;
    }

    public void setTiempoProcesamiento(BigDecimal tiempoProcesamiento) {
        this.tiempoProcesamiento = tiempoProcesamiento;
    }

    public Integer getCodigoSalida() {
        return codigoSalida;
    }

    public void setCodigoSalida(Integer codigoSalida) {
        this.codigoSalida = codigoSalida;
    }

    public boolean isOk() {
        boolean ok = false;
        if (codigoSalida != null) {
            if (codigoSalida == 0) {
                ok = true;
            }
        }
        return ok;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Compresion other = (Compresion) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
