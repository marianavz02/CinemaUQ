package model.entity;

import java.time.LocalDateTime;

public class MovimientoTarjeta {
    private long id;
    private LocalDateTime fecha;
    private double monto;
    private String tipo; // RECARGA, PAGO, REEMBOLSO

    public MovimientoTarjeta(double monto, String tipo) {
        this.id = System.currentTimeMillis();
        this.fecha = LocalDateTime.now();
        this.monto = monto;
        this.tipo = tipo;
    }
}
