package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class SQLCuenta {

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
	public SQLCuenta(PersistenciaParranderos pp)
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
	public long adicionarCuenta(PersistenceManager pm, BigDecimal numeroCuenta, BigDecimal pazSalvo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCuentas() + "(NUMERO_CUENTA, PAZ_SALVO) values (?, ?)");
        q.setParameters(numeroCuenta, pazSalvo);
        return (long) q.executeUnique();            
	}
}
