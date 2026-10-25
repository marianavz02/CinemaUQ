package model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra {
    private long id;
    private Cliente cliente;
    private Funcion funcion;
    private List<Boleta> boletas = new ArrayList<>();
    private List<DetalleConfiteria> confiteria = new ArrayList<>();
    private double total;
    private int puntosAcumulados;
    private LocalDateTime fechaCompra;


    protected Compra() {}
//agregado reciente
    public Cliente getCliente() {
        return cliente;
    }

    // Setters (visibilidad paquete o protected)
    void setCliente(Cliente cliente) { this.cliente = cliente; }
    void setFuncion(Funcion funcion) { this.funcion = funcion; }
    void addBoletas(List<Boleta> boletas) { this.boletas.addAll(boletas); }
    void addConfiteria(List<DetalleConfiteria> confiteria) { this.confiteria.addAll(confiteria); }
    public void setTotal(double total) { this.total = total; }
    void setFechaCompra(LocalDateTime fecha) { this.fechaCompra = fecha; }

    public List<Boleta> getBoletas() {
        return boletas;
    }

    public List<DetalleConfiteria> getConfiteria() {
        return confiteria;
    }

    public double getTotal() {
        return total;
    }

    public Long getId() {
        return id;
    }
}