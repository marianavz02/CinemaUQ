package model.usecases;

import model.entity.Administrador;
import model.CinemaUQContext;
import model.entity.Cliente;

public class PagoTarjetaUseCaseProxy implements IPagoTarjetaUseCase {

    private final PagoTarjetaUseCase servicioReal;

    public PagoTarjetaUseCaseProxy(PagoTarjetaUseCase servicioReal) {
        this.servicioReal = servicioReal;
    }

    @Override
    public boolean verificarSaldoSuficiente(Cliente cliente, double montoTotal) {
        // Método de acceso público, el proxy simplemente delega la ejecución
        return servicioReal.verificarSaldoSuficiente(cliente, montoTotal);
    }

    @Override
    public void debitarSaldo(Cliente cliente, double montoTotal) throws Exception {
        // Método de acceso público para procesar compras
        servicioReal.debitarSaldo(cliente, montoTotal);
    }

    @Override
    public void recargarTarjeta(Cliente cliente, double monto) throws Exception {
        // Lógica del Proxy: Control de acceso estricto
        var usuarioActual = CinemaUQContext.getInstance().getCurrentUser();

        if (usuarioActual instanceof Administrador) {
            servicioReal.recargarTarjeta(cliente, monto);
            System.out.println("Recarga exitosa aprobada por el administrador.");
        } else {
            throw new SecurityException("Acceso denegado: Solo un Administrador autenticado puede realizar recargas de saldo.");
        }
    }
}