package model;

import java.time.LocalDate;

public abstract class ReporteService {
    public abstract void generarReporteVentasPorPeriodo(LocalDate inicio, LocalDate fin);
    public abstract void generarReporteEntradasPorPelicula(Pelicula pelicula);
    public abstract void generarReporteOcupacionSalas();

}