package model.usecases;

import model.entity.Cliente;
import model.entity.Compra;
import model.entity.Boleta;
import model.entity.DetalleConfiteria;

public class ProcesarCompraUseCase implements IProcesarCompraUseCase {
    private final IPagoTarjetaUseCase pagoServicio;
    private final IGestionPuntosUseCase puntosServicio;

    // Inyección de dependencias (DIP)
    public ProcesarCompraUseCase(IPagoTarjetaUseCase pagoServicio, IGestionPuntosUseCase puntosServicio) {
        this.pagoServicio = pagoServicio;
        this.puntosServicio = puntosServicio;
    }

    @Override
    public Compra ejecutarCompra(Compra compra, Cliente cliente) throws Exception {
        double totalCompra = calcularTotal(compra);
        compra.setTotal(totalCompra); // REVISAR ;-;

        if (!pagoServicio.verificarSaldoSuficiente(cliente, totalCompra)) {
            throw new Exception("Saldo Insuficiente en la tarjeta virtual");
        }

        pagoServicio.debitarSaldo(cliente, totalCompra);
        puntosServicio.acumularPuntos(cliente, compra); // RN-004 ejecutada

        return compra;
    }

    private double calcularTotal(Compra compra) {
        double total = 0.0;
        if (compra.getBoletas() != null) {
            for (Boleta b : compra.getBoletas()) {
                total += b.getPrecio();
            }
        }
        if (compra.getConfiteria() != null) {
            for (DetalleConfiteria d : compra.getConfiteria()) {
                total += (d.getProducto().getPrecio() * d.getCantidad());
            }
        }
        return total;
    }
}