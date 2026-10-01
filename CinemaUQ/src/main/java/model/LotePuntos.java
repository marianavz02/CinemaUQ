package model;

import java.time.LocalDate;

public class LotePuntos {
    private long id;
    private int cantidad;
    private LocalDate fechaObtencion;
    private LocalDate fechaVencimiento;

    public boolean estaVencido() {
        return LocalDate.now().isAfter(fechaVencimiento);
    }
}