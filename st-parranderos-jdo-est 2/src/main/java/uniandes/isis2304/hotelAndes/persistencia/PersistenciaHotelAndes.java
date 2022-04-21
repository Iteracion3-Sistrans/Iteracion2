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

package uniandes.isis2304.hotelAndes.persistencia;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import uniandes.isis2304.hotelAndes.negocio.ClienteEmail;
import uniandes.isis2304.hotelAndes.negocio.ClienteGeneral;
import uniandes.isis2304.hotelAndes.negocio.Cuenta;
import uniandes.isis2304.hotelAndes.negocio.DisponibilidadHabitaciones;
import uniandes.isis2304.hotelAndes.negocio.ReservaHabitacion;

/**
 * Clase para el manejador de persistencia del proyecto HotelAndes
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLBar, SQLBebedor, SQLBebida, SQLGustan, SQLSirven, SQLTipoBebida y SQLVisitan, que son 
 * las que realizan el acceso a la base de datos
 * 
 * @author Germán Bravo
 */
public class PersistenciaHotelAndes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaHotelAndes.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaHotelAndes instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaHotelAndes
	 */
	private SQLUtil sqlUtil;
	
	private SQLClienteGeneral sqlClienteGeneral;
	
	private SQLClienteEmail sqlClienteEmail;
	
	private SQLDisponibilidadHabitaciones sqlDisponibilidadHabitaciones;
	
	private SQLReservaHabitacion sqlReservaHabitacion;
	
	private SQLCuenta sqlCuentas;

	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaHotelAndes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("HotelAndes");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("");
		tablas.add ("CLIENTES_GENERAL");
		tablas.add ("CLIENTES_EMAIL");
		tablas.add ("BAR");
		tablas.add ("BEBEDOR");
		tablas.add ("GUSTAN");
		tablas.add ("SIRVEN");
		tablas.add ("VISITAN");
}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaHotelAndes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaHotelAndes existente - Patrón SINGLETON
	 */
	public static PersistenciaHotelAndes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaHotelAndes ();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaHotelAndes existente - Patrón SINGLETON
	 */
	public static PersistenciaHotelAndes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaHotelAndes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlCuentas = new SQLCuenta (this);
		sqlReservaHabitacion = new SQLReservaHabitacion(this);
		sqlDisponibilidadHabitaciones = new SQLDisponibilidadHabitaciones(this);
		sqlClienteEmail = new SQLClienteEmail(this);
		sqlClienteGeneral = new SQLClienteGeneral(this);
		sqlUtil = new SQLUtil(this);
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de parranderos
	 */
	//TODO: Eliminar la secuencia
	public String darSeqParranderos ()
	{
		return tablas.get (0);
	}

	public String darTablaCuentas()
	{
		return tablas.get(10);
	}
	public String darTablaReservasHabitaciones()
	{
		return tablas.get(45);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de TipoBebida de parranderos
	 */
	public String darTablaTipoBebida ()
	{
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebida de parranderos
	 */
	public String darTablaBebida ()
	{
		return tablas.get (2);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bar de parranderos
	 */
	public String darTablaBar ()
	{
		return tablas.get (3);
	}

	public String darTablaClienteGeneral() {
		return tablas.get(8);
	}

	public String darTablaClienteEmail () {
		return tablas.get(7);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebedor de parranderos
	 */
	public String darTablaBebedor ()
	{
		return tablas.get (4);
	}

	public String darTablaDisponibilidadHabitaciones() {
		return tablas.get(13);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de parranderos
	 */
	public String darTablaGustan ()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Sirven de parranderos
	 */
	public String darTablaSirven ()
	{
		return tablas.get (6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaVisitan ()
	{
		return tablas.get (7);
	}
	
	/**
	 * Transacción para el generador de secuencia de HotelAndes
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de HotelAndes
	 */
	private long nextval ()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/*****************
	 * 
	 * METODOS PARA MANEJAR LOS CLIENTES
	 ****************/

	public ClienteGeneral adicionarClienteGeneral(String nombreCliente, String tipoDoc, String numDoc)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlClienteGeneral.adicionarClienteGeneral(pm, nombreCliente, tipoDoc, numDoc);
			tx.commit();
			
			log.trace("Inserción de ClienteGeneral: " + nombreCliente + ": " + tuplasInsertadas + " tuplas insertadas");

			return new ClienteGeneral(tipoDoc, numDoc, nombreCliente);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}	
	}

	public ClienteEmail adicionarClienteEmail(String tipoDocumento, String numeroDocumento , String email)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlClienteEmail.adicionarClienteEmail(pm, tipoDocumento, numeroDocumento, email);
			tx.commit();
			
			log.trace("Inserción de ClienteEmail: " + email + ": " + tuplasInsertadas + " tuplas insertadas");

			return new ClienteEmail(tipoDocumento, numeroDocumento, email);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}	
	}


	/***********************************
	 * METODOS PARA MANEJAR LAS RESERVAS
	 ***********************************/

	public ReservaHabitacion adicionarReserva(Timestamp fechaInicioTimestamp, Timestamp fechaFinTimestamp, BigDecimal numPersonas, String planPago, BigDecimal habitacion, BigDecimal idServicioHab, String recepTipoDoc, String recepNumDoc)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			BigDecimal id = new BigDecimal(nextval());
			long tuplasInsertadas = sqlReservaHabitacion.adicionarReserva(pm, id ,fechaInicioTimestamp, fechaFinTimestamp, numPersonas, planPago, habitacion, idServicioHab, recepTipoDoc, recepNumDoc);
			tx.commit();
			
			log.trace("Inserción de Reserva: " + fechaInicioTimestamp + ": " + tuplasInsertadas + " tuplas insertadas");

			return new ReservaHabitacion(id,fechaInicioTimestamp, fechaFinTimestamp, numPersonas, planPago, habitacion, idServicioHab, recepTipoDoc, recepNumDoc);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}	
	}

	public Cuenta adicionarCuenta()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			BigDecimal id = new BigDecimal(nextval());
			long tuplasInsertadas = sqlCuentas.adicionarCuenta(pm, id, BigDecimal.ZERO);
			tx.commit();
			
			log.trace("Inserción de Cuenta: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Cuenta(id, BigDecimal.ZERO);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}	
	}

	public ReservaHabitacion abrirCuentaReserva (BigDecimal idReserva, BigDecimal idCuenta)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlReservaHabitacion.abrirCuentaReserva(pm, idReserva, idCuenta);
			tx.commit();
			
			log.trace("Inserción de CuentaReserva: " + idReserva + ": " + tuplasInsertadas + " tuplas insertadas");

			return new ReservaHabitacion(idReserva, null, null, null, null, null, null, null, null);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}	
	}

	/*****************************************
	 * METODOS PARA MANEJAR LA DISPONIBILIDAD DE HABITACIONES
	 ******************************************************/


    public List<DisponibilidadHabitaciones> darDisponibilidadHabitaciones(int tipoHabitacion)
	{
		return sqlDisponibilidadHabitaciones.darDisponibilidadHabitaciones(pmf.getPersistenceManager(), tipoHabitacion);
	}
    
}
