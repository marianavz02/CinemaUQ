package model;

import java.util.List;

public class ProductoConfiteriaFactory {
    public static ProductoIndividual createProductoIndividual(int id, String nombre, double precio, String tipo) {
        return new ProductoIndividual(id, nombre, precio, tipo);
    }

    public static Combo createCombo(int id, String nombre, List<ProductoConfiteria> productos, double descuento) {
        return new Combo(id, nombre, productos, descuento);
    }
}