package model;

public class Cliente extends Usuario {
    private TarjetaVirtual tarjetaVirtual;
    private PuntosLealtad puntosLealtad;

    public Cliente(long id, String name, String email, String password) {
        super(id, name, email, password);
        this.puntosLealtad = new PuntosLealtad(this);
    }


//    public void registrarse() { /* ... */ }
//    public void consultarCartelera() { /* ... */ }
//    public void seleccionarAsientos(Funcion funcion) { /* ... */ }
//    public void agregarConfiteria(List<DetalleConfiteria> productos) { /* ... */ }
//    public void realizarCompra(CompraBuilder builder) { /* ... */ }
//    public void pagarConTarjetaVirtual() { /* ... */ }
//    public void consultarSaldoTarjeta() { /* ... */ }
//    public void revisarHistorialCompras() { /* ... */ }
//    public void solicitarCancelacion(Compra compra) { /* ... */ }
//    public void redimirPuntos(int puntos) { /* ... */ }
}