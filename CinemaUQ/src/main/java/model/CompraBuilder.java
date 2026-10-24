package model;

import java.time.LocalDateTime;
import java.util.List;

public class CompraBuilder {
    private Compra compra;

    public CompraBuilder() {
        this.compra = new Compra();
    }

    public CompraBuilder paraCliente(Cliente cliente) {
        compra.setCliente(cliente);
        return this;
    }

    public CompraBuilder deFuncion(Funcion funcion) {
        compra.setFuncion(funcion);
        return this;
    }

    public CompraBuilder conAsientos(List<Boleta> boletas) {
        compra.addBoletas(boletas);
        return this;
    }

    public CompraBuilder conConfiteria(List<DetalleConfiteria> confiteria) {
        compra.addConfiteria(confiteria);
        return this;
    }
//PEND
    public Compra build() {
        if (this.compra.getCliente() == null){
            throw new IllegalStateException("La compra debe tener un cliente asociado");
        }
        compra.setFechaCompra(LocalDateTime.now());
        return compra;
    }
}