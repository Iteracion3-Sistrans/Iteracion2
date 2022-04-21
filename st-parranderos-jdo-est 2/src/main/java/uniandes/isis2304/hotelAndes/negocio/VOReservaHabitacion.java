package uniandes.isis2304.hotelAndes.negocio;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface VOReservaHabitacion {
   
    public BigDecimal getId();
    public Timestamp getFechaInicioTimestamp();
    public Timestamp getFechaFinTimestamp();
    public BigDecimal getNumPersonas();
    public String getPlanPago();
    public BigDecimal getHabitacion();
    public BigDecimal getIdServicioHab();
    public String getRecepTipoDoc();
    public String getRecepNumDo();
    
    public void setId(BigDecimal id);
    public void setFechaInicioTimestamp(Timestamp fechaInicioTimestamp);
    public void setFechaFinTimestamp(Timestamp fechaFinTimestamp);
    public void setNumPersonas(BigDecimal numPersonas);
    public void setPlanPago(String planPago);
    public void setHabitacion(BigDecimal habitacion);
    public void setIdServicioHab(BigDecimal idServicioHab);
    public void setRecepTipoDoc(String recepTipoDoc);
    public void setRecepNumDo(String recepNumDo);
    
}
