package model.usecases;

import model.Cliente;
import model.Compra;

public interface IGestionPuntosUseCase {
    void acumularPuntos(Cliente cliente,Compra compra) throws Exception;
    boolean redimirPuntos(Cliente cliente,int puntosRequeridos) throws Exception;
}
