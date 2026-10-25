package model.decorator;

import model.entity.ProductoConfiteria;

public abstract class ProductoConfiteriaDecorator extends ProductoConfiteria {
    protected ProductoConfiteria productoBase;

    public ProductoConfiteriaDecorator(ProductoConfiteria productoBase) {
        this.productoBase = productoBase;
        this.id = productoBase.getId();
        // Hereda el nombre base inicialmente
        this.nombre = productoBase.getNombre();
    }
}