package model.usecases;

import java.util.List;
import model.Compra;

public interface IExportadorReporte {
    void exportar(String titulo, List<Compra> datos) throws Exception;
}