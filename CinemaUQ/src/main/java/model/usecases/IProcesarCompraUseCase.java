package model.usecases;

import model.Cliente;
import model.Compra;

public interface IProcesarCompraUseCase {
    Compra ejecutarCompra(Compra compra, Cliente cliente) throws Exception;
}

