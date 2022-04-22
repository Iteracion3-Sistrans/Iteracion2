package uniandes.isis2304.hotelAndes.persistencia;
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
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.hotelAndes.negocio.DisponibilidadHabitaciones;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto TIPO DE BEBIDA de HotelAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 *
 */

public class SQLDisponibilidadHabitaciones {
   	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaHotelAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaHotelAndes pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLDisponibilidadHabitaciones(PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un TIPOBEBIDA a la base de datos de HotelAndes
	 * @param pm - El manejador de persistencia
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @param nombre - El nombre del tipo de bebida
	 * @return EL número de tuplas insertadas
	 */
	public List<DisponibilidadHabitaciones> darDisponibilidadHabitaciones(PersistenceManager pm, int idHabitacion, Timestamp fechaInicio) 
	{
        String idHabit = Integer.toString(idHabitacion);
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaDisponibilidadHabitaciones() + " WHERE (FECHA_DISPONIBLE IS NULL OR FECHA_DISPONIBLE <= ?) AND ID_SERVICIO_TIPO_HABITACION = ? ");
        q.setParameters(fechaInicio, idHabitacion);
		q.setResultClass(DisponibilidadHabitaciones.class);
        return (List<DisponibilidadHabitaciones>) q.executeList();            
	}    
}
