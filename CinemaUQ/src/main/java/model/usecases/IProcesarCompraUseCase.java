package model.usecases;

import model.entity.Cliente;
import model.entity.Compra;

public interface IProcesarCompraUseCase {
    Compra ejecutarCompra(Compra compra, Cliente cliente) throws Exception;
}

