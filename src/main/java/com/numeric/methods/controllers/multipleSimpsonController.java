package com.numeric.methods.controllers;

import java.io.IOException;
import java.util.List;
import com.numeric.methods.App;
import com.numeric.methods.logic.MultipleSimpson;

import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class multipleSimpsonController {

    // Componentes de la vista inyectados por FXML
    @FXML private TextField functionField;
    @FXML private TextField initialPointField; // Límite inferior (a)
    @FXML private TextField finalPointField;   // Límite superior (b)
    @FXML private TextField segmentsField;     // Número de subintervalos (n)
    @FXML private TextField resultField;
    
    @FXML private TableView<MultipleSimpson.ResultRow> resultsTable;
    @FXML private TableColumn<MultipleSimpson.ResultRow, Integer> iterationColumn;
    @FXML private TableColumn<MultipleSimpson.ResultRow, Double> xiColumn;
    @FXML private TableColumn<MultipleSimpson.ResultRow, Double> fxiColumn;
    @FXML private TableColumn<MultipleSimpson.ResultRow, Integer> weightColumn;
    @FXML private TableColumn<MultipleSimpson.ResultRow, Double> contributionColumn;

    private final ObservableList<MultipleSimpson.ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        iterationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIteration()));
        xiColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getXi()));
        fxiColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getFxi()));
        weightColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getWeight()));
        contributionColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getContribution()));
        resultsTable.setItems(tableData);
    }

    @FXML
    private void calculateMultipleSimpson() {
        tableData.clear();
        if (resultField != null) resultField.clear();

        String function = functionField.getText().trim();
        String initialP = initialPointField.getText().trim();
        String finalP = finalPointField.getText().trim();
        String segments = segmentsField.getText().trim();

        if (function.isEmpty() || initialP.isEmpty() || finalP.isEmpty() || segments.isEmpty()) {
            showErrorAlert("Por favor, complete todos los campos.");
            return;
        }

        try {
            double a = Double.parseDouble(initialP); // Límite inferior
            double b = Double.parseDouble(finalP);   // Límite superior
            int n = Integer.parseInt(segments);

            if (a == b) {
                showErrorAlert("El límite inferior y el límite superior no pueden ser iguales.");
                return;
            }

            if (a > b) {
                showErrorAlert("El límite inferior debe ser menor que el límite superior.");
                return;
            }

            if (n % 2 != 0) {
                showErrorAlert("El número de subintervalos (n) debe ser estrictamente un número par.");
                return;
            }

            MultipleSimpson multipleSimpson = new MultipleSimpson(function, a, b, n);
            
            double integralValue = multipleSimpson.calculateIntegral();
            if (resultField != null) {
                resultField.setText(String.format("%.6f", integralValue));
            }

            List<MultipleSimpson.ResultRow> results = multipleSimpson.generateTableData();
            tableData.addAll(results);

            showInfoAlert("Cálculo completado exitosamente.");
        
        } catch (NumberFormatException e) {
            showErrorAlert("Por favor, ingrese valores numéricos válidos (límites reales y segmentos enteros).");
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