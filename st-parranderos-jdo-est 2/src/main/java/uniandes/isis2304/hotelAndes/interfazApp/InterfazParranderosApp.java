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

package uniandes.isis2304.hotelAndes.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.hotelAndes.negocio.DisponibilidadHabitaciones;
import uniandes.isis2304.hotelAndes.negocio.HotelAndes;
import uniandes.isis2304.hotelAndes.negocio.VOClienteEmail;
import uniandes.isis2304.hotelAndes.negocio.VOClienteGeneral;
import uniandes.isis2304.hotelAndes.negocio.VOCuenta;
import uniandes.isis2304.hotelAndes.negocio.VODisponibilidadHabitaciones;
import uniandes.isis2304.hotelAndes.negocio.VOReservaHabitacion;

/**
 * Clase principal de la interfaz
 * @author Germán Bravo
 */
@SuppressWarnings("serial")

public class InterfazParranderosApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazParranderosApp.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * Asociación a la clase principal del negocio.
     */
    private HotelAndes hotelAndes;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazParranderosApp( )
    {
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        hotelAndes = new HotelAndes (tableConfig);
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "HotelAndes App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "HotelAndes APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
     
	/* ****************************************************************
	 * 			RF 7: REGISTRAR UNA RESERVA POR PARTE DE UN CLIENTE
	 *****************************************************************/
    /**
	 * Se registra una nueva reserva en la base de datos segun la informacion ingresada por el usuario
     */
	
	public void registrarReservaAlojamiento()
	{
		try {
			String nombreCliente = JOptionPane.showInputDialog("Ingrese el nombre del cliente");

			Object[] options = {"CC", "TI"};
			Object selectionObject = JOptionPane.showInputDialog(null, "Seleccione el tipo de documento", "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			String tipoDocumento = selectionObject.toString();

			String numeroDocumento = JOptionPane.showInputDialog("Ingrese el numero de documento");
		
			String email = JOptionPane.showInputDialog("Ingrese el email");

			String fechaInicio = JOptionPane.showInputDialog("Ingrese la fecha de inicio de la reserva (ej: 2022-07-03)");

			String fechaFin = JOptionPane.showInputDialog("Ingrese la fecha de fin de la reserva (ej: 2022-07-20)");
			Timestamp fechaInicioTimestamp = Timestamp.valueOf(fechaInicio + " 00:00:00");
			Timestamp fechaFinTimestamp = Timestamp.valueOf(fechaFin + " 00:00:00");

			Object[] options2 = {"1.Simple", "2.Doble", "3.Triple", "4.Cuadruple","5.Suite","6.Suite Premium", "7.Suite Luxury", "8.Suite Superior", "9.Suite Superior Luxury", "10.Suite Superior Premium"};
			Object selectionObject2 = JOptionPane.showInputDialog(null, "Seleccione el tipo de habitacion", "Menu", JOptionPane.PLAIN_MESSAGE, null, options2, options2[0]);
			char tipoHabitacion = selectionObject2.toString().charAt(0);
			int tipHabitacion = tipoHabitacion - 48;

			String cantPersonas = JOptionPane.showInputDialog("Ingrese la cantidad de personas");
			int cantPersonasInt = Integer.parseInt(cantPersonas);

			System.out.println(tipHabitacion);
			
			if(nombreCliente != null && tipoDocumento != null && numeroDocumento != null && email != null && fechaInicio != null && fechaFin != null && cantPersonas != null) {
				if (cantPersonasInt < 0 && (tipHabitacion < 1 || tipHabitacion > 10))
				{
					throw new Exception("La cantidad de personas debe ser mayor a 0 o la habitacion no fue valida");
				}				
				else
				{
					//Adicionar un cliente a la base de datos	
					VOClienteGeneral tb = hotelAndes.adicionarClienteGeneral(nombreCliente, tipoDocumento, numeroDocumento, email);
					VOClienteEmail tb2 = hotelAndes.adicionarClienteEmail(tipoDocumento, numeroDocumento, email);
					if(tb == null || tb2 == null) {
						throw new Exception("El cliente no se pudo crear en la base de datos ");
					}
					String resultado = "Se pudo crear al cliente " + tb;
					resultado += "\nSe pudo crear al cliente " + tb2;
					panelDatos.actualizarInterfaz(resultado);
					//Validar disponibilidad habitaciones	
					List <VODisponibilidadHabitaciones> disponibilidad = hotelAndes.darVODisponibilidadHabitaciones(tipHabitacion, fechaInicioTimestamp);
					if (disponibilidad.size() == 0)
					{
						throw new Exception("No hay disponibilidad para el tipo de habitacion seleccionado");
					}

					String result2 = "Hay disponibilidad para las siguientes habitaciones" + disponibilidad;
					panelDatos.actualizarInterfaz(result2);
					BigDecimal habitacion = hotelAndes.darDisponibilidadHabitaciones(tipHabitacion, fechaInicioTimestamp);
					panelDatos.actualizarInterfaz("Se asignó la habitación" + habitacion);	

					//Agregar la reserva a la base de datos
					BigDecimal numPersonas = new BigDecimal(cantPersonasInt);
					String planPago = "Preferencial";
					BigDecimal idServicioHab = new BigDecimal(tipHabitacion);
					String recepTipoDoc = "CC";
					String recepNumDoc = "1234567891";
					
					VOReservaHabitacion reserva = hotelAndes.adicionarReserva(fechaInicioTimestamp, fechaFinTimestamp, numPersonas, planPago, habitacion,idServicioHab, recepTipoDoc, recepNumDoc);
					
					if(reserva == null)
					{
						throw new Exception("La reserva no se pudo crear en la base de datos");
					}
					
					String result3 = "Se pudo crear la reserva " + reserva;
					panelDatos.actualizarInterfaz(result3);

				}
			}
			else{
				throw new Exception("No se ingresaron todos los datos");
			}
			
		} catch (Exception e) {

			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);

		}
	} 
	
	/*****************************************************
	 * 			RF9: REGISTRAR LA LLEGADA DE UN CLIENTE AL HOTEL
	*****************************************************/
	public void registrarLlegadaCliente()
	{
		try {
			
			String idReservaStr = JOptionPane.showInputDialog("Ingrese el ID de la reserva");

			BigDecimal idReserva = new BigDecimal(idReservaStr);

			BigDecimal cuenta = hotelAndes.adicionarCuentaBig();

			if (cuenta == null)
			{
				throw new Exception("No se pudo crear la cuenta");
			}
			String resultado = "Se pudo crear la cuenta con id: " + cuenta;
			panelDatos.actualizarInterfaz(resultado);

			VOReservaHabitacion reserva = hotelAndes.abrirCuentaReserva(idReserva, cuenta);
			
			if(reserva == null)
			{
				throw new Exception("La reserva no se pudo abrir en la base de datos");
			}

			String result2 = "Se pudo abrir la cuenta de la reserva " + reserva;
			panelDatos.actualizarInterfaz(result2);
			
		} catch (Exception e) {
			//TODO: handle exception
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	

	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de HotelAndes
	 */
	public void mostrarLogParranderos ()
	{
		mostrarArchivo ("hotelAndes.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de hotelAndes
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogParranderos ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("hotelAndes.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de hotelAndes ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de hotelAndes
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
			//TODO: VOLVER A GENERAR ESTE MÉTODO
			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			//String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			//resultado += eliminados [0] + " Gustan eliminados\n";
			//resultado += eliminados [1] + " Sirven eliminados\n";
			//resultado += eliminados [2] + " Visitan eliminados\n";
			//resultado += eliminados [3] + " Bebidas eliminadas\n";
			//resultado += eliminados [4] + " Tipos de bebida eliminados\n";
			//resultado += eliminados [5] + " Bebedores eliminados\n";
			//resultado += eliminados [6] + " Bares eliminados\n";
			//resultado += "\nLimpieza terminada";
			//panelDatos.actualizarInterfaz(resultado);

		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}
	
	/**
	 * Muestra el modelo conceptual de HotelAndes
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual HotelAndes.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de HotelAndes
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD HotelAndes.pdf");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaParranderos.sql");
	}
	
	/**
	 * Muestra la arquitectura de referencia para HotelAndes
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}
	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}
	
	/**
     * Muestra la información acerca del desarrollo de esta apicación
     */
    public void acercaDe ()
    {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versión 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: HotelAndes Uniandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Germán Bravo\n";
		resultado += " * Julio de 2018\n";
		resultado += " * \n";
		resultado += " * Revisado por: Claudia Jiménez, Christian Ariza\n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
    }
    

	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/

    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y hotelAndes.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazParranderosApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazParranderosApp interfaz = new InterfazParranderosApp( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
