package model.entity;

public class DetalleConfiteria {
    private long id;
    private ProductoConfiteria producto;
    private int cantidad;


    public DetalleConfiteria(long id, ProductoConfiteria producto, int cantidad) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProductoConfiteria getProducto() {
        return producto;
    }

    public void setProducto(ProductoConfiteria producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}