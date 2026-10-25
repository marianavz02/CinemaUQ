package model.usecases;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import model.entity.Compra;

public class ExportadorTXT implements IExportadorReporte {
    @Override
    public void exportar(String titulo, List<Compra> datos) throws Exception {
        try (PrintWriter out = new PrintWriter(new FileWriter(titulo + ".txt"))) {
            out.println("=== " + titulo + " ===");
            for (Compra c : datos) {
                out.println("Compra ID: " + c.getId() + " | Total: $" + c.getTotal());
            }
        }
    }
}