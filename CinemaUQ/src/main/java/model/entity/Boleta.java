package model.entity;

public class Boleta {
    private long id;
    private Asiento asiento;
    private double precio;


    public Boleta(long id, Asiento asiento, double precio) {
        this.id = id;
        this.asiento = asiento;
        this.precio = precio;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }


}