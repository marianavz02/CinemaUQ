package model;

public abstract class ProductoConfiteria {
    protected int id;
    protected String nombre;
    protected double precio;

    public double getPrecio() {
        return precio; }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
