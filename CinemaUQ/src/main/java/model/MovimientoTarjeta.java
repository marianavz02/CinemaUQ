package model;

import java.time.LocalDateTime;

public class MovimientoTarjeta {
    private long id;
    private LocalDateTime fecha;
    private double monto;
    private String tipo; // RECARGA, PAGO, REEMBOLSO
}