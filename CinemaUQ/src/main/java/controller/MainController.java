package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.CinemaUQContext;
import model.ProductoConfiteriaFactory;
import model.entity.*;
import model.facade.SistemaCompraFacade;
import model.usecases.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;



public class MainController {

    @FXML private Label lblEstadoSesion;
    @FXML private TabPane mainTabPane;

    // Login
    @FXML private Tab tabLogin;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblLoginError;

    // Pestañas
    @FXML private Tab tabCartelera;
    @FXML private Tab tabAsientos;
    @FXML private Tab tabConfiteria;
    @FXML private Tab tabPago;
    @FXML private Tab tabTarjeta;

    // Cartelera
    @FXML private ListView<Funcion> listaFunciones;
    @FXML private Label lblCarteleraMsg;

    // Asientos
    @FXML private GridPane gridAsientos;
    @FXML private Label lblAsientosMsg;

    // Confitería
    @FXML private ListView<ProductoConfiteria> listaProductos;
    @FXML private CheckBox chkExtraQueso;
    @FXML private TextField txtCantidadProducto;
    @FXML private Label lblConfiteriaResumen;

    // Pago
    @FXML private TextArea txtAreaResumen;
    @FXML private Label lblTotalPagar;
    @FXML private Label lblSaldoTarjeta;
    @FXML private Label lblResultadoPago;

    // Tarjeta
    @FXML private TextField txtMontoRecarga;
    @FXML private Label lblRecargaStatus;

    // Datos del flujo actual
    private Cliente clienteActual;
    private List<Funcion> funcionesDisponibles;
    private Funcion funcionSeleccionada;
    private List<Asiento> asientosSeleccionadosTemp = new ArrayList<>();
    private List<DetalleConfiteria> confiteriaSeleccionadaTemp = new ArrayList<>();

    private SistemaCompraFacade sistemaCompraFacade;
    private IPagoTarjetaUseCase pagoUseCase;

    @FXML
    public void initialize() {
        IPagoTarjetaUseCase pagoUseCase;
        pagoUseCase = new PagoTarjetaUseCaseProxy(new PagoTarjetaUseCase());
        GestionPuntosUseCase puntosUseCase = new GestionPuntosUseCase();

        ProcesarCompraUseCase procesarCompraUseCase = new ProcesarCompraUseCase(pagoUseCase, puntosUseCase);
        sistemaCompraFacade = new SistemaCompraFacade(procesarCompraUseCase);

        inicializarDatosPrueba();
    }
    private void inicializarDatosPrueba() {
        funcionesDisponibles = new ArrayList<>();

        // Crear una sala con 12 asientos (3 filas x 4 columnas)
        List<Asiento> asientosSala1 = new ArrayList<>();
        long asientoId = 1;
        for (int f = 1; f <= 3; f++) {
            for (int c = 1; c <= 4; c++) {
                Asiento a = new Asiento();
                asientosSala1.add(a);
            }
        }
        Sala sala1 = new Sala(1, 12, asientosSala1);

        Pelicula p1 = new Pelicula(1, "Dune: Parte Dos", "Ciencia Ficción", 166, "Una épica travesía en Arrakis.", "url");
        Pelicula p2 = new Pelicula(2, "Kung Fu Panda 4", "Animación", 94, "El Guerrero Dragón regresa.", "url");

        funcionesDisponibles.add(new Funcion(1, p1, sala1, LocalDate.now(), LocalTime.of(18, 0)));
        funcionesDisponibles.add(new Funcion(2, p2, sala1, LocalDate.now(), LocalTime.of(20, 30)));

        listaFunciones.getItems().addAll(funcionesDisponibles);

// Cargar productos de confitería usando la Fábrica (Factory Method)
        List<ProductoConfiteria> prodsBase = new ArrayList<>();
        prodsBase.add(ProductoConfiteriaFactory.createProductoIndividual(1, "Gaseosa Grande", 8000.0, "Bebida"));
        prodsBase.add(ProductoConfiteriaFactory.createProductoIndividual(2, "Crispetas de Sal", 12000.0, "Snack"));

// Uso extendido del Factory: Creación de un Combo con descuento
        List<ProductoConfiteria> productosCombo = new ArrayList<>();
        productosCombo.add(ProductoConfiteriaFactory.createProductoIndividual(3, "Gaseosa Mediana", 6000.0, "Bebida"));
        productosCombo.add(ProductoConfiteriaFactory.createProductoIndividual(4, "Perro Caliente", 10000.0, "Snack"));
        prodsBase.add(ProductoConfiteriaFactory.createCombo(5, "Combo Pareja", productosCombo, 0.15));

        listaProductos.getItems().addAll(prodsBase);


        // DATO QUEMADO PARA PROBAR Instancia cliente con tarjeta virtual inicializada con saldo de prueba
        Cliente clientePrueba = new Cliente(101, "Carlos Andrés", "m@", "123");
        clientePrueba.getTarjetaVirtual().recargar(60000.0); // 60.000 COP iniciales
        CinemaUQContext.getInstance().agreagarUsuario(clientePrueba);
    }

    @FXML
    protected void onLoginClick() {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (email.isEmpty() || password.isEmpty()) {
            lblLoginError.setText("Ingrese correo y contraseña.");
            return;
        }

        try {
            AutenticacionUseCase authUseCase = new AutenticacionUseCase();

            // Se hace un casting ya que login retorna un Usuario genérico
            clienteActual = (Cliente) authUseCase.login(email, password);

            CinemaUQContext.getInstance().setCurrentUser(clienteActual);
            lblEstadoSesion.setText("Sesión Activa: " + clienteActual.getName() + " | Saldo: $" + clienteActual.getTarjetaVirtual().getSaldo());
            lblLoginError.setText("");

            // Habilitar pestañas del sistema
            tabCartelera.setDisable(false);
            tabTarjeta.setDisable(false);
            mainTabPane.getSelectionModel().select(tabCartelera);

        } catch (Exception e) {
            // Captura la excepción lanzada si el correo/contraseña son incorrectos
            lblLoginError.setText(e.getMessage());
        }
    }

    @FXML
    protected void onIrAAsientosClick() {
        funcionSeleccionada = listaFunciones.getSelectionModel().getSelectedItem();
        if (funcionSeleccionada == null) {
            lblCarteleraMsg.setText("Debe seleccionar una función de la cartelera.");
            return;
        }
        lblCarteleraMsg.setText("");
        asientosSeleccionadosTemp.clear();
        construirMatrizAsientos();

        tabAsientos.setDisable(false);
        mainTabPane.getSelectionModel().select(tabAsientos);
    }

    private void construirMatrizAsientos() {
        gridAsientos.getChildren().clear();
        List<Asiento> asientos = funcionSeleccionada.getSala().getAsientos();

        int col = 0;
        int row = 0;
        for (Asiento asiento : asientos) {
            Button btnAsiento = new Button("Asiento " + asiento.hashCode() % 100);
            btnAsiento.setPrefSize(90, 40);

            String estadoActual = asiento.getEstado();
            updateButtonStyle(btnAsiento, estadoActual);

            btnAsiento.setOnAction(e -> {

                if ("Ocupado".equals(asiento.getEstado())) {
                    return;
                }

                if (asientosSeleccionadosTemp.contains(asiento)) {
                    asientosSeleccionadosTemp.remove(asiento);
                    asiento.liberar(); // Usa tu método existente ("Disponible")
                    updateButtonStyle(btnAsiento, asiento.getEstado());
                } else {
                    asientosSeleccionadosTemp.add(asiento);
                    asiento.reservar(); // Usa tu método existente ("Seleccionado")
                    updateButtonStyle(btnAsiento, asiento.getEstado());
                }
            });

            gridAsientos.add(btnAsiento, col, row);
            col++;
            if (col > 3) {
                col = 0;
                row++;
            }
        }
    }

    private void updateButtonStyle(Button btn, String estado) {
        if ("Ocupado".equals(estado)) {
            btn.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white;");
            btn.setDisable(true);
        } else if ("Seleccionado".equals(estado)) {
            btn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
        } else {
            btn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        }
    }

    @FXML
    protected void onConfirmarAsientosClick() {
        if (asientosSeleccionadosTemp.isEmpty()) {
            lblAsientosMsg.setText("Debe seleccionar al menos un asiento.");
            return;
        }
        lblAsientosMsg.setText("");
        tabConfiteria.setDisable(false);
        mainTabPane.getSelectionModel().select(tabConfiteria);
    }

    @FXML
    protected void onAgregarProductoClick() {
        ProductoConfiteria prod = listaProductos.getSelectionModel().getSelectedItem();
        if (prod == null) return;

        try {
            int cant = Integer.parseInt(txtCantidadProducto.getText());

            // APLICACIÓN PATRÓN DECORATOR: Envolver el objeto original para sumar el precio/descripción
            if (chkExtraQueso.isSelected()) {
                prod = new model.decorator.AdicionQuesoDecorator(prod);
            }

            DetalleConfiteria detalle = new DetalleConfiteria(System.currentTimeMillis(), prod, cant);
            confiteriaSeleccionadaTemp.add(detalle);
            lblConfiteriaResumen.setText("Productos en el carrito: " + confiteriaSeleccionadaTemp.size());

            // Limpiar para el siguiente producto
            chkExtraQueso.setSelected(false);
            txtCantidadProducto.setText("1");
        } catch (NumberFormatException e) {
            lblConfiteriaResumen.setText("Error: Cantidad inválida");
        }
    }

    @FXML
    protected void onIrAPagoClick() {
        tabPago.setDisable(false);
        mainTabPane.getSelectionModel().select(tabPago);
        calcularYMostrarResumen();
    }

    private double calcularTotalCompra() {
        double total = 0.0;
        for (Asiento a : asientosSeleccionadosTemp) {
            total += 18000.0; // Precio estándar por boleta
        }
        for (DetalleConfiteria d : confiteriaSeleccionadaTemp) {
            total += (d.getProducto().getPrecio() * d.getCantidad());
        }
        return total;
    }

    private void calcularYMostrarResumen() {
        double total = calcularTotalCompra();
        lblTotalPagar.setText(String.valueOf(total));
        lblSaldoTarjeta.setText(String.valueOf(clienteActual.getTarjetaVirtual().getSaldo()));

        StringBuilder sb = new StringBuilder();
        sb.append("Película: ").append(funcionSeleccionada.getPelicula().getTitulo()).append("\n");
        sb.append("Asientos seleccionados: ").append(asientosSeleccionadosTemp.size()).append("\n");
        sb.append("Items de confitería: ").append(confiteriaSeleccionadaTemp.size()).append("\n");
        sb.append("Total a cancelar: $").append(total);
        txtAreaResumen.setText(sb.toString());
    }

    @FXML
    protected void onPagarCompraClick() {
        try {
            double total = calcularTotalCompra();

            List<Boleta> boletas = new ArrayList<>();
            for (Asiento a : asientosSeleccionadosTemp) {
                boletas.add(new Boleta(System.currentTimeMillis(), a, 18000.0));
            }

            // Uso del patrón Builder para ensamblar la compra
            Compra compra = new CompraBuilder()
                    .paraCliente(clienteActual)
                    .deFuncion(funcionSeleccionada)
                    .conAsientos(boletas)
                    .conConfiteria(confiteriaSeleccionadaTemp)
                    .build();

            // Procesar a través de la Fachada (Facade)
            sistemaCompraFacade.confirmarTransaccion(compra, clienteActual);

            // Marcar físicamente los asientos como ocupados en la sala para que no se puedan volver a vender
            for (Asiento a : asientosSeleccionadosTemp) {
                a.ocupar(); // Cambia el estado interno a "Ocupado"
            }

            lblResultadoPago.setText("¡Compra exitosa! Puntos acumulados con éxito.");
            lblSaldoTarjeta.setText(String.valueOf(clienteActual.getTarjetaVirtual().getSaldo()));
            lblEstadoSesion.setText("Sesión Activa: " + clienteActual.getName() + " | Saldo: $" + clienteActual.getTarjetaVirtual().getSaldo());

        } catch (Exception e) {
            lblResultadoPago.setText("Error en pago: " + e.getMessage());
            lblResultadoPago.setStyle("-fx-text-fill: #c0392b;");
        }
    }

    @FXML
    protected void onReiniciarFlujoClick() {
        // Limpiar selecciones temporales y volver a la cartelera
        asientosSeleccionadosTemp.clear();
        confiteriaSeleccionadaTemp.clear();
        lblResultadoPago.setText("");
        txtAreaResumen.clear();

        // Bloquear de nuevo las pestañas siguientes para obligar a un flujo nuevo
        tabAsientos.setDisable(true);
        tabConfiteria.setDisable(true);
        tabPago.setDisable(true);

        mainTabPane.getSelectionModel().select(tabCartelera);
    }

    @FXML
    protected void onRecargarClick() {
        try {
            double monto = Double.parseDouble(txtMontoRecarga.getText());

            // 2. Usar el caso de uso (Proxy) en lugar de modificar la entidad directamente
            pagoUseCase.recargarTarjeta(clienteActual, monto);

            lblRecargaStatus.setText("¡Recarga de $" + monto + " exitosa!");
            lblRecargaStatus.setStyle("-fx-text-fill: #27ae60;");
            lblEstadoSesion.setText("Sesión Activa: " + clienteActual.getName() + " | Saldo: $" + clienteActual.getTarjetaVirtual().getSaldo());
            txtMontoRecarga.clear();
        } catch (SecurityException e) {
            // 3. Capturar y mostrar la excepción lanzada por el Proxy
            lblRecargaStatus.setText(e.getMessage());
            lblRecargaStatus.setStyle("-fx-text-fill: #c0392b;");
        } catch (Exception e) {
            lblRecargaStatus.setText("Monto inválido.");
        }
    }
}