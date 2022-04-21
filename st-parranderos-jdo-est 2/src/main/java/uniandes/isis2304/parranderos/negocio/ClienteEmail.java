/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto BAR del negocio de los Parranderos
 *
 */
public class ClienteEmail implements VOClienteEmail
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
    
    private String email;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public ClienteEmail() 
	{
		this.tipoDocumento= "";
        this.numeroDocumento = "";
        this.email = "";
	}

	public ClienteEmail(String tipoDocumento, String numeroDocumento, String email) 
	{
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.email = email;
	}
	/**
	 * @return Una cadena de caracteres con la información 
	 */
	@Override
	public String toString() 
	{
		return "Cliente general[Tipo Documento=" + this.tipoDocumento + ", email=" + this.email;
	}

	public boolean equals(Object tipo) 
	{
		ClienteEmail cg = (ClienteEmail) tipo;
        return (this.tipoDocumento.equals(cg.getTipoDocumento()) && this.numeroDocumento.equals(cg.getNumeroDocumento()));
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
