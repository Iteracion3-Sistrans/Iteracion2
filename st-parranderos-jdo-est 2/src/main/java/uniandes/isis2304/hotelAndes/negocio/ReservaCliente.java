package uniandes.isis2304.hotelAndes.negocio;

import java.math.BigDecimal;

public class ReservaCliente implements VOReservaCliente {
   
    /*****************************
     * Atributos
     ****************************/
    
   String clienteTipoDoc;
   String clienteNumDoc;
   BigDecimal idReserva; 
   
   /*****************************
    * Constructor
    ****************************/ 

    //Constructor por defecto
    public ReservaCliente()
    {
    	this.clienteNumDoc= "";
    	this.clienteTipoDoc= "";
    	this.idReserva = new BigDecimal(0);
    }

    public ReservaCliente(String cliente_tipo_doc, String cliente_num_doc, BigDecimal id_reserva) {
        this.clienteTipoDoc = cliente_tipo_doc;
        this.clienteNumDoc = cliente_num_doc;
        this.idReserva = id_reserva;
    }
    
    /*****************************
     * Getters y Setters 
     ****************************/

    public String getClienteTipoDoc() {
        return clienteTipoDoc; 
    }
    
    public String getClienteNumDoc() {
        return clienteNumDoc; 
    }
    
    public BigDecimal getIdReserva() {
        return idReserva; 
    }

    public void setClienteTipoDoc(String clienteTipoDoc) {
        this.clienteTipoDoc = clienteTipoDoc;
    }

    public void setClienteNumDoc(String clienteNumDoc) {
        this.clienteNumDoc = clienteNumDoc;
    }

    public void setIdReserva(BigDecimal idReserva) {
        this.idReserva = idReserva;
    }
    
    /*****************************
     * Metodos
     ****************************/

    @Override
    public String toString() {
        return "ReservaCliente [clienteTipoDoc=" + clienteTipoDoc + ", clienteNumDoc=" + clienteNumDoc + ", idReserva=" + idReserva + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReservaCliente other = (ReservaCliente) obj;
        if (clienteNumDoc == null) {
            if (other.clienteNumDoc != null)
                return false;
        } else if (!clienteNumDoc.equals(other.clienteNumDoc))
            return false;
        if (clienteTipoDoc == null) {
            if (other.clienteTipoDoc != null)
                return false;
        } else if (!clienteTipoDoc.equals(other.clienteTipoDoc))
            return false;
        if (idReserva == null) {
            if (other.idReserva != null)
                return false;
        } else if (!idReserva.equals(other.idReserva))
            return false;
        
        if (idReserva.equals(other.getIdReserva()) && clienteTipoDoc.equals(other.getClienteTipoDoc()) && clienteNumDoc.equals(other.getClienteNumDoc()))
            return true;
        else
        {
            return false; 
        }
    }

}
