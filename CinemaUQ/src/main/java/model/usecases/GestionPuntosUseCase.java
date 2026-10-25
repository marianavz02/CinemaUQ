package model.usecases;

import model.entity.Administrador;
import model.entity.Cliente;
import model.entity.Compra;
import model.entity.LotePuntos;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;


public class GestionPuntosUseCase implements IGestionPuntosUseCase{
    @Override
    public void acumularPuntos(Cliente cliente, Compra compra) throws Exception {
        double total = compra.getTotal();
        int puntosGanados = (int) (total / 10000) * 10; // RN-004

        if (puntosGanados > 0) {
            LotePuntos nuevoLote = new LotePuntos(
                    System.currentTimeMillis(),
                    puntosGanados,
                    LocalDate.now(),
                    LocalDate.now().plusDays(365) // Vigencia de 1 año
            );
            cliente.getPuntosLealtad().agregarLote(nuevoLote);
        }
    }

    @Override
    public boolean redimirPuntos(Cliente cliente, int puntosRequeridos) throws Exception {
        List<LotePuntos> lotes = cliente.getPuntosLealtad().getLotes();
      //anterior  List<LotePuntos> lotes = null; // Sustituir por la línea superior real

        int totalDisponible = lotes.stream()
                .filter(l -> !l.estaVencido())
                .mapToInt(LotePuntos::getCantidad)
                .sum();

        if (totalDisponible < puntosRequeridos) {
            throw new Exception("Puntos insuficientes o vencidos.");
        }

        // Lógica FIFO
        int restantes = puntosRequeridos;
        Iterator<LotePuntos> iterator = lotes.iterator();

        while (iterator.hasNext() && restantes > 0) {
            LotePuntos lote = iterator.next();
            if (lote.estaVencido()) continue;

            if (lote.getCantidad() <= restantes) {
                restantes -= lote.getCantidad();
                iterator.remove();
            } else {
                lote.setCantidad(lote.getCantidad() - restantes);
                restantes = 0;
            }
        }
        return true;
    }
}
