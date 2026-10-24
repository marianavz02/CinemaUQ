package model.usecases;

import model.Cliente;
import model.Compra;

public class ProcesarCompraUseCase implements IProcesarCompraUseCase {
    private final IPagoTarjetaUseCase pagoServicio;

    //inyeccion de dependencias DIP
    public ProcesarCompraUseCase(IPagoTarjetaUseCase pagoServicio) {
        this.pagoServicio = pagoServicio;
    }

    @Override
    public Compra ejecutarCompra (Compra compra, Cliente cliente) throws Exception {
        double totalCompra = calcularTotal(compra);
        //vrerificar saldo de la tarjeta
        if (!pagoServicio.verificarSaldoSuficiente(cliente, totalCompra)) {
            throw new Exception("Saldo Insuficiente en la tarjeta virtual");
        }
        pagoServicio.debitarSaldo(cliente, totalCompra);
        //agregar el calculo de puntos, pendiente
        //int puntosAcumulados = calcularPuntos(totalCompra);
        return compra;
    }

    private double calcularTotal(Compra compra) {
        //pend
        return 0.0;
    }
}
