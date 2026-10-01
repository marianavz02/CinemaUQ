package model;

public class ProductoIndividual extends ProductoConfiteria {
    private String tipo; // Bebida, Snack, Dulce

    protected ProductoIndividual(int id, String nombre, double precio, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
    }
}