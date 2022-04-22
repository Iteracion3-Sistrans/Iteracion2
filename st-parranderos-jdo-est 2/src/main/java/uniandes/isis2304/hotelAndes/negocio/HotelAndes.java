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

package uniandes.isis2304.hotelAndes.negocio;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;
import uniandes.isis2304.hotelAndes.persistencia.PersistenciaHotelAndes;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Germán Bravo
 */
public class HotelAndes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(HotelAndes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaHotelAndes pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public HotelAndes ()
	{
		pp = PersistenciaHotelAndes.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public HotelAndes (JsonObject tableConfig)
	{
		pp = PersistenciaHotelAndes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}

	
	/* ****************************************************************
	 * 			Métodos para manejar los CLIENTES
	 *****************************************************************/

	/**
	 * Adiciona un cliente a la base de datos
	 * */

	public ClienteGeneral adicionarClienteGeneral(String nombreCliente, String tipoDoc, String numDoc, String email)
	{
		log.info("Adicionando cliente " + nombreCliente + "Tipo de documento " + tipoDoc + "Numero de documento " + numDoc + "Email " + email);
		ClienteGeneral cliente = pp.adicionarClienteGeneral(nombreCliente, tipoDoc, numDoc);
		log.info("Cliente adicionado");
		return cliente;
	}
	
	public ClienteEmail adicionarClienteEmail (String tipoDocumento, String numeroDocumento, String email)
	{
		log.info ("Adicionando cliente email " + tipoDocumento + " " + numeroDocumento + " " + email);
		ClienteEmail cliente = pp.adicionarClienteEmail (tipoDocumento, numeroDocumento, email);
		log.info ("Cliente adicionado");
		return cliente;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las RESERVAS
	 *  *****************************************************************/

	public ReservaHabitacion adicionarReserva(Timestamp fechaInicioTimestamp, Timestamp fechaFinTimestamp, BigDecimal numPersonas, String planPago, BigDecimal habitacion, BigDecimal idServicioHab, String recepTipoDoc, String recepNumDoc)
	{
		log.info("Adicionando reserva " + fechaInicioTimestamp + " " + fechaFinTimestamp + " " + numPersonas + " " + planPago + " " + habitacion + " " + idServicioHab + " " + recepTipoDoc + " " + recepNumDoc);	
		ReservaHabitacion reseva = pp.adicionarReserva(fechaInicioTimestamp, fechaFinTimestamp, numPersonas, planPago, habitacion, idServicioHab, recepTipoDoc, recepNumDoc);
		log.info("Reserva adicionada");
		return reseva;
	}

	public Cuenta adicionarCuenta()
	{
		log.info("Adicionando cuenta");
		Cuenta cuenta = pp.adicionarCuenta();
		log.info("Cuenta adicionada");
		return cuenta;
	}
	
	public BigDecimal adicionarCuentaBig()
	{
		log.info("Adicionando cuenta");
		Cuenta cuenta = pp.adicionarCuenta();
		log.info("Cuenta adicionada");
		return cuenta.getNumeroCuenta();
	}
	
	public ReservaHabitacion abrirCuentaReserva(BigDecimal idReserva, BigDecimal idCuenta)
	{
		log.info("Abriendo cuenta reserva " + idReserva + " " + idCuenta);
		ReservaHabitacion reserva = pp.abrirCuentaReserva(idReserva, idCuenta);
		log.info("Cuenta abierta");
		return reserva;
	}

	 /*****************************************************************
	  * 			Métodos para manejar las HABITACIONES 
	  ****************************************************************/

	public List <VODisponibilidadHabitaciones> darVODisponibilidadHabitaciones (int tipHabitacion, Timestamp fechaInicio)
	{
		log.info ("Generando los VO de Disponibilidad Habitaciones");        
        List<VODisponibilidadHabitaciones> voTipos = new LinkedList<> ();
        for (DisponibilidadHabitaciones tb : pp.darDisponibilidadHabitaciones(tipHabitacion, fechaInicio))
		{
			voTipos.add (tb);
		}
        log.info ("Generando los VO de Tipos de bebida: " + voTipos.size() + " existentes");
        return voTipos;
	}	

	public BigDecimal darDisponibilidadHabitaciones (int tipHabitacion, Timestamp fechaInicio)
	{
		return pp.darDisponibilidadHabitaciones (tipHabitacion, fechaInicio).get(0).getNumeroHabitacion();
	} 

	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de HotelAndes
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
//	public long [] limpiarParranderos ()
//	{
//		//TODO: CORREGIR
//       log.info ("Limpiando la BD de HotelAndes");
//        //long [] borrrados = pp.limpiarParranderos();	
//        log.info ("Limpiando la BD de HotelAndes: Listo!");
//        return borrrados;
//	}
	
	
	
}
