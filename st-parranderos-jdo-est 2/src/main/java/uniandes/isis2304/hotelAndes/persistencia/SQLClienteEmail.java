/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: HotelAndes Uniandes
 * @version 1.0
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.hotelAndes.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto TIPO DE BEBIDA de HotelAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 *
 */

public class SQLClienteEmail {
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
	public SQLClienteEmail(PersistenciaHotelAndes pp)
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
	public long adicionarClienteEmail(PersistenceManager pm, String tipoDocumento, String numeroDocumento, String email) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaClienteEmail() + " (TIPO_DOC, NUM_DOC, EMAIL) values (?, ?, ?)");
        q.setParameters(tipoDocumento, numeroDocumento, email);
        return (long) q.executeUnique();            
	} 
}
