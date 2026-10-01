package model;

import java.util.List;

public class TarjetaVirtual {
    private long id;
    private String numeroTarjeta;
    private double saldo;
    private String estado;
    private List<MovimientoTarjeta> movimientos;

    public void recargar(double monto) {
        this.saldo += monto;
        // Registrar movimiento
    }

    public void descontar(double monto) {
        if(this.saldo >= monto && "Activa".equals(estado)) {
            this.saldo -= monto;
            // Registrar movimiento
        } else {
            throw new RuntimeException("Saldo insuficiente o tarjeta inactiva");
        }
    }
}