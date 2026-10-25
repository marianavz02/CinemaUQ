package model.entity;

import java.util.ArrayList;
import java.util.List;

public class PuntosLealtad {
    private long id;
    private Cliente cliente;
    private int totalPuntos;
    private List<LotePuntos> lotes;

    public PuntosLealtad(Cliente cliente) {
        this.cliente = cliente;
        this.lotes = new ArrayList<>();
    }
    //pendiente
    //public void acumularPuntos(Compra compra) { /* Lógica FIFO */ }
    //public void redimirPuntos(int puntos) { /* Lógica FIFO validando vigencia */ }

    public void agregarLote(LotePuntos lote) {
        if (lote != null) {
            this.lotes.add(lote);
            this.totalPuntos += lote.getCantidad();
        }
    }


    public List<LotePuntos> getLotes() {
        return lotes;
    }



}
