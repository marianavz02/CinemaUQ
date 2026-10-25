package model.usecases;

import model.entity.Cliente;
import model.exceptions.TarjetaInvalidaException;

public class PagoTarjetaUseCase implements IPagoTarjetaUseCase {

    @Override
    public boolean verificarSaldoSuficiente(Cliente cliente, double montoTotal) {
        var tarjeta = cliente.getTarjetaVirtual();
        return tarjeta != null && tarjeta.getSaldo() >= montoTotal && "Activa".equals(tarjeta.getEstado());
    }

    @Override
    public void debitarSaldo(Cliente cliente, double montoTotal) throws Exception {
        if (cliente.getTarjetaVirtual() == null) {
            throw new TarjetaInvalidaException("El cliente no tiene una tarjeta virtual asociada.");
        }

        // La entidad TarjetaVirtual ejecuta la validación, la resta y registra el "PAGO"
        cliente.getTarjetaVirtual().descontar(montoTotal);
    }

    @Override
    public void recargarTarjeta(Cliente cliente, double monto) throws Exception {
        if (cliente.getTarjetaVirtual() == null) {
            throw new TarjetaInvalidaException("El cliente no tiene una tarjeta virtual asociada.");
        }

        // La entidad suma el saldo y registra automáticamente la "RECARGA"
        cliente.getTarjetaVirtual().recargar(monto);
    }
}