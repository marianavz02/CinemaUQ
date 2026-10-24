package model.usecases;
import java.util.List;
import model.Compra;

public class ExportadorTXT implements IExportadorReporte {
    @Override
    public void exportar(String titulo, List<Compra> datos) throws Exception {
        //PENDIENTE
        // Lógica de escritura a archivo plano de texto
        System.out.println("Generando TXT: " + titulo);
    }
}