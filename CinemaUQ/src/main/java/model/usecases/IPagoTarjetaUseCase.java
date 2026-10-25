package model.usecases;

import model.entity.Cliente;

public interface IPagoTarjetaUseCase {
    boolean verificarSaldoSuficiente(Cliente cliente, double motoTotal);
    void debitarSaldo (Cliente cliente, double montoTotal) throws Exception;
    void recargarTarjeta(Cliente cliente, double monto) throws Exception;
}
