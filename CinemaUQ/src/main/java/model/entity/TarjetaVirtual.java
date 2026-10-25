package model.entity;

import model.exceptions.TarjetaInvalidaException;

import java.util.ArrayList;
import java.util.List;

public class TarjetaVirtual {
    private long id;
    private String numeroTarjeta;
    private double saldo = 0.0;
    private String estado = "Activa";
    private List<MovimientoTarjeta> movimientos = new ArrayList<MovimientoTarjeta>();

    public void recargar(double monto) {
        this.saldo += monto;
        this.movimientos.add(new MovimientoTarjeta(monto, "RECARGA"));
    }


    public void descontar(double monto) throws TarjetaInvalidaException {
        if(this.saldo >= monto && "Activa".equals(this.estado)) {
            this.saldo -= monto;
            this.movimientos.add(new MovimientoTarjeta(monto, "PAGO"));
        } else {
            throw new TarjetaInvalidaException("Saldo insuficiente o tarjeta inactiva");
        }
    }

    public double getSaldo() {
        return this.saldo;
    }

    public String getEstado() {
        return this.estado;
    }



}
