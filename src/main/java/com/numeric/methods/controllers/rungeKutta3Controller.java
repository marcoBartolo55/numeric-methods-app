package com.numeric.methods.controllers;

import java.io.IOException;
import java.util.List;
import com.numeric.methods.App;
import com.numeric.methods.logic.RungeKutta3;
import com.numeric.methods.logic.RungeKutta3.ResultRow;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class rungeKutta3Controller {

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
    @FXML private TableColumn<ResultRow, String> k3Column;

    // Lista observable mapeada a la tabla
    private ObservableList<ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        
        iterationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIteration()));
        xColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getX()));
        yColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getY()));
        k1Column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getK1())));
        k2Column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getK2())));
        k3Column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getK3())));
        resultTable.setItems(tableData);

    }

    @FXML
    private void calculateRungeKutta3() {
        tableData.clear();
        
        try {
            
            double x0 = Double.parseDouble(initialXField.getText());
            double y0 = Double.parseDouble(initialYField.getText());
            double xf = Double.parseDouble(finalXField.getText());
            double h = Double.parseDouble(stepSizeField.getText());
            String function = differentialEquationField.getText();

            RungeKutta3 rungeKutta3Method = new RungeKutta3(function, x0, y0, xf, h);
            List<RungeKutta3.ResultRow> results = rungeKutta3Method.generateTableData();
            showInfoAlert("Cálculo completado. Se han generado " + results.size() + " filas de resultados.");
            tableData.addAll(results);

        } catch (NumberFormatException e) {
            
            showErrorAlert("Por favor, ingresa valores numéricos válidos para x0, y0, xf y h.");

        } catch (Exception e) {
            
            showErrorAlert("Ocurrió un error: " + e.getMessage());

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
