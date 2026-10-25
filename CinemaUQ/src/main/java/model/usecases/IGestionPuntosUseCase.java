package model.usecases;

import model.entity.Cliente;
import model.entity.Compra;

public interface IGestionPuntosUseCase {
    void acumularPuntos(Cliente cliente,Compra compra) throws Exception;
    boolean redimirPuntos(Cliente cliente,int puntosRequeridos) throws Exception;
}
