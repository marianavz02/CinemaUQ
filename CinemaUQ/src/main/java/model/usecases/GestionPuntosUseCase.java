package model.usecases;

import model.Cliente;
import model.Compra;

public class GestionPuntosUseCase implements IGestionPuntosUseCase{
    @Override
    public void acumularPuntos (Cliente cliente, Compra compra) throws Exception{
        //cALCULO 1 AÑO VIGENCIA
    }

    @Override
    public boolean redimirPuntos (Cliente cliente,int puntosRequeridos) throws Exception{
        //logica FIFO
        return true;
    }
}
