package model.entity;

public class Asiento {
    private long id;
    private int fila;
    private int columna;
    private String tipo;
    private String estado; // Disponible, Ocupado, Seleccionado

    public void reservar() { this.estado = "Seleccionado"; }
    public void ocupar() { this.estado = "Ocupado"; }
    public void liberar() { this.estado = "Disponible"; }
}