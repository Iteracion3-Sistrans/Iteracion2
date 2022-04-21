/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: HotelAndes Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.hotelAndes.negocio;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Clase para modelar el concepto BAR del negocio de los HotelAndes
 *
 */
public class DisponibilidadHabitaciones implements VODisponibilidadHabitaciones{
   	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 */
    private BigDecimal numeroHabitacion;
    
    private BigDecimal idServicioTipo;
    
    private Timestamp fechaDisponible;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public DisponibilidadHabitaciones() 
	{
		this.numeroHabitacion= new BigDecimal(0); 
        this.idServicioTipo = new BigDecimal(0);
        this.fechaDisponible = new Timestamp(0);
	}

	public DisponibilidadHabitaciones(BigDecimal numeroHabitacion, BigDecimal idServicioTipo, Timestamp fechaDisponible) 
	{
        this.numeroHabitacion = numeroHabitacion;
        this.idServicioTipo = idServicioTipo;
        this.fechaDisponible = fechaDisponible;

	}
	/**
	 * @return Una cadena de caracteres con la información 
	 */
	@Override
	public String toString() 
	{
		return "Habitacion disponible: [Numero habitacion " + this.numeroHabitacion + ", idServicioTipo: " + this.idServicioTipo + "fechaDisponible= " + this.fechaDisponible + "]";
	}

	public boolean equals(Object tipo) 
	{
		DisponibilidadHabitaciones cg = (DisponibilidadHabitaciones) tipo;
        return (this.numeroHabitacion.equals(cg.getNumeroHabitacion()) && this.idServicioTipo.equals(cg.getIdServicioTipo()));
	}

	//Getters y setters
    
    public BigDecimal getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(BigDecimal numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public BigDecimal getIdServicioTipo() {
        return idServicioTipo;
    }

    public void setIdServicioTipo(BigDecimal idServicioTipo) {
        this.idServicioTipo = idServicioTipo;
    }

    public Timestamp getFechaDisponible() {
        return fechaDisponible;
    }

    public void setFechaDisponible(Timestamp fechaDisponible) {
        this.fechaDisponible = fechaDisponible;
    }

}
