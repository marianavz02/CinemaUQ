package model.entity;

import java.util.List;

public class Sala {
    private int id;
    private int capacidad;
    private List<Asiento> asientos;


    public Sala(int id, int capacidad, List<Asiento> asientos) {
        this.id = id;
        this.capacidad = capacidad;
        this.asientos = asientos;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }
}

