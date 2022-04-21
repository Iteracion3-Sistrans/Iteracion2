package uniandes.isis2304.parranderos.negocio;

import java.math.BigDecimal;

public interface VOCuenta {
   
    public BigDecimal getNumeroCuenta();
    public BigDecimal getPazSalvo();
    
    public void setNumeroCuenta(BigDecimal numeroCuenta);
    public void setPazSalvo(BigDecimal pazSalvo);
    
}
