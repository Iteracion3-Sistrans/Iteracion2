package uniandes.isis2304.hotelAndes.negocio;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

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

public class ReservaHabitacion implements VOReservaHabitacion{
  
    private BigDecimal id;
    private Timestamp fechaInicioTimestamp;
    private Timestamp fechaFinTimestamp;
    BigDecimal numPersonas;
    String planPago; 
    BigDecimal habitacion;
    BigDecimal idServicioHab;
    String recepTipoDoc;
    String recepNumDo;
    
   public ReservaHabitacion()
   {
         this.id = new BigDecimal(0);
         this.fechaInicioTimestamp = new Timestamp(0);
         this.fechaFinTimestamp = new Timestamp(0);
         this.numPersonas = new BigDecimal(0);
         this.planPago = "";
         this.habitacion = new BigDecimal(0);
         this.idServicioHab = new BigDecimal(0);
         this.recepTipoDoc = "";
         this.recepNumDo = "";
   } 
   
   public ReservaHabitacion (BigDecimal id,Timestamp fechaInicioTimestamp, Timestamp fechaFinTimestamp, BigDecimal numPersonas, String planPago, BigDecimal habitacion, BigDecimal idServicioHab, String recepTipoDoc, String recepNumDo)
   {
       this.id = id; 
       this.fechaInicioTimestamp = fechaInicioTimestamp;
       this.fechaFinTimestamp = fechaFinTimestamp;
       this.numPersonas = numPersonas;
       this.planPago = planPago;
       this.habitacion = habitacion;
       this.idServicioHab = idServicioHab;
       this.recepTipoDoc = recepTipoDoc;
       this.recepNumDo = recepNumDo;
   }

   //Getters y setters

    public Timestamp getFechaInicioTimestamp() {
        return fechaInicioTimestamp;
    }

    public void setFechaInicioTimestamp(Timestamp fechaInicioTimestamp) {
        this.fechaInicioTimestamp = fechaInicioTimestamp;
    }

    public Timestamp getFechaFinTimestamp() {
        return fechaFinTimestamp;
    }

    public void setFechaFinTimestamp(Timestamp fechaFinTimestamp) {
        this.fechaFinTimestamp = fechaFinTimestamp;
    }

    public BigDecimal getNumPersonas() {
        return numPersonas;
    }

    public void setNumPersonas(BigDecimal numPersonas) {
        this.numPersonas = numPersonas;
    }

    public String getPlanPago() {
        return planPago;
    }

    public void setPlanPago(String planPago) {
        this.planPago = planPago;
    }

    public BigDecimal getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(BigDecimal habitacion) {
        this.habitacion = habitacion;
    }

    public BigDecimal getIdServicioHab() {
        return idServicioHab;
    }

    public void setIdServicioHab(BigDecimal idServicioHab) {
        this.idServicioHab = idServicioHab;
    }

    public String getRecepTipoDoc() {
        return recepTipoDoc;
    }

    public void setRecepTipoDoc(String recepTipoDoc) {
        this.recepTipoDoc = recepTipoDoc;
    }

    public String getRecepNumDo() {
        return recepNumDo;
    }

    public void setRecepNumDo(String recepNumDo) {
        this.recepNumDo = recepNumDo;
    }
    
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }



    //toString
    @Override
    public String toString() {
        return "ID RESERVA : " + id+ "\nReservaHabitacion{" + "fechaInicioTimestamp=" + fechaInicioTimestamp + ", fechaFinTimestamp=" + fechaFinTimestamp + ", numPersonas=" + numPersonas + ", planPago=" + planPago + ", habitacion=" + habitacion + ", idServicioHab=" + idServicioHab + ", recepTipoDoc=" + recepTipoDoc + ", recepNumDo=" + recepNumDo + '}';
    }

    //Equals
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReservaHabitacion other = (ReservaHabitacion) obj;
        
        if (Objects.equals(this.id, other.id)) {
            return true;
        }
        
        return false;
    }
}
