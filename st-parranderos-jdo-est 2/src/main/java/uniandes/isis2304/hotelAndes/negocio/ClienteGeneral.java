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

/**
 * Clase para modelar el concepto BAR del negocio de los HotelAndes
 *
 * @author Germán Bravo
 */
public class ClienteGeneral implements VOClienteGeneral
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 */
    private String tipoDocumento;

	/**
	 */
	private String numeroDocumento;
    
    private String nombre;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public ClienteGeneral() 
	{
		this.tipoDocumento= "";
        this.numeroDocumento = "";
        this.nombre = "";
	}

	public ClienteGeneral(String tipoDocumento, String numeroDocumento, String nombre) 
	{
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
	}
	/**
	 * @return Una cadena de caracteres con la información 
	 */
	@Override
	public String toString() 
	{
		return "Cliente general[Tipo Documento=" + this.tipoDocumento + ", nombre=" + this.nombre + ", numeroDocumento=" + this.numeroDocumento + "]";
	}

	public boolean equals(Object tipo) 
	{
		ClienteGeneral cg = (ClienteGeneral) tipo;
        return (this.tipoDocumento.equals(cg.tipoDocumento) && this.numeroDocumento.equals(cg.numeroDocumento));
	}

	//Getters y setters
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
