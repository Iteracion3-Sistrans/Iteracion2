package uniandes.isis2304.hotelAndes.negocio;

import java.math.BigDecimal;

public class Cuenta implements VOCuenta {
    
    private BigDecimal numeroCuenta;
    private BigDecimal pazSalvo;

    public Cuenta() {
        this.numeroCuenta = new BigDecimal(0);
        this.pazSalvo = new BigDecimal(0);
    }

    public Cuenta (BigDecimal numeroCuenta, BigDecimal pazSalvo) {
        this.numeroCuenta = numeroCuenta;
        this.pazSalvo = pazSalvo;
    }

    //Getters y setters
    public BigDecimal getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(BigDecimal numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public BigDecimal getPazSalvo() {
        return pazSalvo;
    }

    public void setPazSalvo(BigDecimal pazSalvo) {
        this.pazSalvo = pazSalvo;
    }

    @Override
    public String toString() {
        return "Cuenta [numeroCuenta=" + numeroCuenta + ", pazSalvo=" + pazSalvo + "]";
    }

    @Override
    public boolean equals (Object cuenta)
    {
        if(cuenta == null)
        {
            return false;
        }
        else{
            if(!(cuenta instanceof Cuenta))
            {
                return false;
            }
            else{
                Cuenta cuenta2 = (Cuenta) cuenta;
                return this.numeroCuenta.equals(cuenta2.getNumeroCuenta());
            }
        }
    }
}
