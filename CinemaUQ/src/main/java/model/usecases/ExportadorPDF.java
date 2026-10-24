package model.usecases;

import model.Compra;
import java.util.List;

public class ExportadorPDF implements IExportadorReporte {
    @Override
    public void exportar(String titulo, List<Compra> datos) throws Exception {
        //PENDIENTE
        // Lógica de exportación a PDF usando librería externa tal vez
        System.out.println("Generando PDF: " + titulo);
    }
}