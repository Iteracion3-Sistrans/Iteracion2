package uniandes.isis2304.parranderos.persistencia;
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto TIPO DE BEBIDA de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 *
 */

public class SQLReservaHabitacion {
       	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaParranderos.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaParranderos pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLReservaHabitacion(PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un TIPOBEBIDA a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @param nombre - El nombre del tipo de bebida
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarReserva(PersistenceManager pm, BigDecimal id,Timestamp fechaInicioTimestamp, Timestamp fechaFinTimestamp, BigDecimal numPersonas, String planPago, BigDecimal habitacion, BigDecimal idServicioHab, String recepTipoDoc, String recepNumDoc) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReservasHabitaciones() + " (ID, FECHA_ENTRADA, FECHA_SALIDA, NUM_PERSONAS, PLAN_PAGO, NUMERO_HABITACION, ID_SERVICIO_TIPO_HABITACION, RECEPCIONISTA_TIPO_DOC, RECEPCIONISTA_NUM_DOC, NUMERO_CUENTA) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, fechaInicioTimestamp, fechaFinTimestamp, numPersonas, planPago, habitacion, idServicioHab, recepTipoDoc, recepNumDoc, null);
        return (long) q.executeUnique();            
	}  
    
    public long abrirCuentaReserva (PersistenceManager pm, BigDecimal id, BigDecimal numeroCuenta)
    {
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaReservasHabitaciones() + " SET NUMERO_CUENTA = ? WHERE ID = ?");
        q.setParameters(numeroCuenta, id);
        return (long) q.executeUnique();
    }
}
