package uniandes.isis2304.hotelAndes.persistencia;

import java.math.BigDecimal;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLReservaCliente {
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
	public SQLReservaCliente(PersistenciaHotelAndes pp)
	{
		this.pp = pp;
	}
	
	public long asociarClienteReserva(PersistenceManager pm, String tipoDocumento, String numeroDocumento, BigDecimal idReserva) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReservasClientes() + " (CLIENTE_TIPO_DOC, CLIENTE_NUM_DOC, ID_RESERVA) values (?, ?, ?)");
        q.setParameters(tipoDocumento, numeroDocumento, idReserva);
        return (long) q.executeUnique();            
	} 
}
