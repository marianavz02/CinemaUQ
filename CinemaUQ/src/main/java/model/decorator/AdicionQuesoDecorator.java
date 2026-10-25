package model.decorator;

import model.entity.ProductoConfiteria;

public class AdicionQuesoDecorator extends ProductoConfiteriaDecorator {

    public AdicionQuesoDecorator(ProductoConfiteria productoBase) {
        super(productoBase);
    }

    @Override
    public double getPrecio() {
        return productoBase.getPrecio() + 2500.0;
    }

    @Override
    public String getNombre() {
        return productoBase.getNombre() + " con Extra Queso";
    }
}