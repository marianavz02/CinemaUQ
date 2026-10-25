package model.facade;

import model.entity.Cliente;
import model.entity.Compra;
import model.usecases.IProcesarCompraUseCase;

public class SistemaCompraFacade {
    private final IProcesarCompraUseCase procesarCompraUseCase;

    // Se inyecta la lógica de compra que a su vez ya maneja tarjeta y puntos
    public SistemaCompraFacade(IProcesarCompraUseCase procesarCompraUseCase) {
        this.procesarCompraUseCase = procesarCompraUseCase;
    }

    public Compra confirmarTransaccion(Compra compra, Cliente cliente) throws Exception {
        // La fachada expone un único método para la vista
        // Ocultando las validaciones de saldo, recálculos y persistencia
        Compra compraProcesada = procesarCompraUseCase.ejecutarCompra(compra, cliente);

        System.out.println("Transacción confirmada para el cliente: " + cliente.getName());
        return compraProcesada;
    }
}