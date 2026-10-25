package model.entity;

import java.time.LocalDate;

public class LotePuntos {
    private long id;
    private int cantidad;
    private LocalDate fechaObtencion;
    private LocalDate fechaVencimiento;

    public LotePuntos(long l, int puntosGanados, LocalDate now, LocalDate localDate) {
    } //REVISAR!!!!

    public boolean estaVencido() {
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
