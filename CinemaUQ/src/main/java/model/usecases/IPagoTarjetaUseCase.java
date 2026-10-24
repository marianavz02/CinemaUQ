package model.usecases;

import model.Cliente;
import model.Compra;

public interface IPagoTarjetaUseCase {
    boolean verificarSaldoSuficiente(Cliente cliente, double motoTotal);
    void debitarSaldo (Cliente cliente, double motoTotal) throws Exception;
}
