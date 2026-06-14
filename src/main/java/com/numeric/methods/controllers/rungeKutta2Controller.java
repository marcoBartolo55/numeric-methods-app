package com.numeric.methods.controllers;

import java.io.IOException;
import java.util.List;
import com.numeric.methods.App;
import com.numeric.methods.logic.RungeKutta2;
import com.numeric.methods.logic.RungeKutta2.ResultRow;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class rungeKutta2Controller {

    // Componentes de la vista inyectados por FXML
    @FXML private TextField differentialEquationField;
    @FXML private TextField initialXField;
    @FXML private TextField initialYField;
    @FXML private TextField finalXField;
    @FXML private TextField stepSizeField;

    @FXML private TableView<ResultRow> resultTable;
    @FXML private TableColumn<ResultRow, Integer> iterationColumn;
    @FXML private TableColumn<ResultRow, String> xColumn;
    @FXML private TableColumn<ResultRow, String> yColumn;
    @FXML private TableColumn<ResultRow, String> k1Column;
    @FXML private TableColumn<ResultRow, String> k2Column;
    @FXML private TableColumn<ResultRow, String> meanSlopeColumn;

    // Lista observable mapeada a la tabla
    private ObservableList<ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        
        iterationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIteration()));
        xColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getX()));
        yColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getY()));
        k1Column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getK1()));
        k2Column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getK2()));
        resultTable.setItems(tableData);

    }

    @FXML
    private void calculateRungeKutta2() {
        tableData.clear();
        
        try {
            
            double x0 = Double.parseDouble(initialXField.getText());
            double y0 = Double.parseDouble(initialYField.getText());
            double xf = Double.parseDouble(finalXField.getText());
            double h = Double.parseDouble(stepSizeField.getText());
            String function = differentialEquationField.getText();

            RungeKutta2 rungeKutta2Method = new RungeKutta2(function, x0, y0, xf, h);
            List<RungeKutta2.ResultRow> results = rungeKutta2Method.generateTableData();
            showInfoAlert("Cálculo completado. Revisa la tabla para ver los resultados.");
            tableData.addAll(results);

        } catch (NumberFormatException e) {
            showErrorAlert("Error: Asegúrate de ingresar valores numéricos válidos para x0, y0, xf y h.");
        } catch (Exception e) {
            showErrorAlert("Error: " + e.getMessage());

        }
    }
    
    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu-ordinary-differential-equations");
    }

     private void showErrorAlert(String message) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Error de entrada");
            alert.setHeaderText("Entrada no válida");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void showInfoAlert(String message) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Resultado");
            alert.setHeaderText("Cálculo completado");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

}
