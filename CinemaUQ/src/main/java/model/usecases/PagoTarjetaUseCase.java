package model.usecases;

import model.Cliente;
import model.MovimientoTarjeta;
import java.time.LocalDateTime;

public class PagoTarjetaUseCase implements IPagoTarjetaUseCase {

    @Override
    public boolean verificarSaldoSuficiente(Cliente cliente, double montoTotal) {
        // Validar que tarjeta.getSaldo() >= montoTotal y estado sea "Activa"
        return true;
    }

    @Override
    public void debitarSaldo(Cliente cliente, double montoTotal) throws Exception {
        if (!verificarSaldoSuficiente(cliente, montoTotal)) {
            throw new Exception("Saldo insuficiente o tarjeta inactiva");
        }

        // tarjeta.setSaldo(tarjeta.getSaldo() - montoTotal);
        registrarMovimiento(cliente, montoTotal, "PAGO");
    }

    public void recargarTarjeta(Cliente cliente, double monto) {
        // Únicamente el administrador invoca este flujo
        // tarjeta.setSaldo(tarjeta.getSaldo() + monto);
        registrarMovimiento(cliente, monto, "RECARGA");
    }

    private void registrarMovimiento(Cliente cliente, double monto, String tipo) {
        // Crear instancia de MovimientoTarjeta y añadirla a la lista de la tarjeta virtual
    }
}