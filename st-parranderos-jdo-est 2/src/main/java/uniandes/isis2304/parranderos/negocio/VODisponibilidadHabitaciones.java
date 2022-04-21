package uniandes.isis2304.parranderos.negocio;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface VODisponibilidadHabitaciones {

    public BigDecimal getNumeroHabitacion();

    public BigDecimal getIdServicioTipo();

    public Timestamp getFechaDisponible();

    public void setNumeroHabitacion(BigDecimal numeroHabitacion);

    public void setIdServicioTipo(BigDecimal idServicioTipo);

    public void setFechaDisponible(Timestamp fechaDisponible);

}
