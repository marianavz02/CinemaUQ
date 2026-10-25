package model.usecases;

import model.entity.Compra;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.List;

public class ExportadorPDF implements IExportadorReporte {
    @Override
    public void exportar(String titulo, List<Compra> datos) throws Exception {
        Document documento = new Document();

        // El archivo se creará en la raíz del proyecto con el nombre del título
        PdfWriter.getInstance(documento, new FileOutputStream(titulo + ".pdf"));

        documento.open();

        documento.add(new Paragraph("=== " + titulo.toUpperCase() + " ==="));
        documento.add(new Paragraph(" ")); // Espacio en blanco

        if (datos != null && !datos.isEmpty()) {
            for (Compra c : datos) {
                documento.add(new Paragraph("ID Compra: " + c.getId() + " | Total: $" + c.getTotal()));
            }
        } else {
            documento.add(new Paragraph("No hay compras registradas para este reporte."));
        }

        documento.close();
    }
}