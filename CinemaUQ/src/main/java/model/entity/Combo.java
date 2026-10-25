package model.entity;

import java.util.List;

public class Combo extends ProductoConfiteria {
    private List<ProductoConfiteria> productos;
    private double descuento;

    public Combo(int id, String nombre, List<ProductoConfiteria> productos, double descuento) {
        this.id = id;
        this.nombre = nombre;
        this.productos = productos;
        this.descuento = descuento;
        calcularPrecioConDescuento();
    }

    private void calcularPrecioConDescuento() {
        double subtotal = productos.stream().mapToDouble(ProductoConfiteria::getPrecio).sum();
        this.precio = subtotal - (subtotal * descuento);
    }
}