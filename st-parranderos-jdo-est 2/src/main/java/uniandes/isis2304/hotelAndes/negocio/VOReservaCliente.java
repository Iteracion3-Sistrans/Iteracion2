package uniandes.isis2304.hotelAndes.negocio;

import java.math.BigDecimal;

public interface VOReservaCliente {
   
    /*****************************
     * Atributos
     ****************************/
    
    public String getClienteTipoDoc();
    
    public String getClienteNumDoc();
    
    public BigDecimal getIdReserva();
    
    /*****************************
     * Metodos
     ****************************/
    
    public void setClienteTipoDoc(String clienteTipoDoc);
    
    public void setClienteNumDoc(String clienteNumDoc);
    
    public void setIdReserva(BigDecimal idReserva);
    
}
