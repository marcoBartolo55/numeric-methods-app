package com.numeric.methods.controllers;

import com.numeric.methods.App;
import com.numeric.methods.logic.GaussianQuadrature;
import com.numeric.methods.logic.GaussianQuadrature.IntegrationResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class gaussianQuadratureController {

    // Componentes de la interfaz FXML
    @FXML private TextField functionField, aField, bField, nField;
    @FXML private TableView<IntegrationResult> resultTable;
    @FXML private TableColumn<IntegrationResult, String> functionColumn, aColumn, bColumn, nColumn, resultColumn;

    private final ObservableList<IntegrationResult> tableData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Enlazar columnas con la clase de resultados
        functionColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getFunction()));
        aColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getA()));
        bColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getB()));
        nColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getN()));
        resultColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getResult()));

        resultTable.setItems(tableData);
    }

    @FXML
    private void calculateIntegration() {
        try {
            // Leer y validar la función
            String func = functionField.getText();
            if (func == null || func.trim().isEmpty()) {
                throw new Exception("El campo de la función no puede estar vacío.");
            }

            // Leer y parsear los límites y puntos
            double a = Double.parseDouble(aField.getText().trim());
            double b = Double.parseDouble(bField.getText().trim());
            int n = Integer.parseInt(nField.getText().trim());

            if (a >= b) {
                throw new Exception("El límite inferior (a) debe ser estrictamente menor que el límite superior (b).");
            }

            // Ejecutar la integración
            double resultValue = GaussianQuadrature.integrate(func, a, b, n);

            // Reflejar en la tabla
            tableData.clear();
            tableData.add(new IntegrationResult(func, a, b, n, resultValue));

        } catch (NumberFormatException e) {
            showErrorAlert("Valores inválidos. Asegúrese de que a, b sean números y n sea un entero.");
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de validación");
        alert.setHeaderText("No se pudo calcular la integral");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void switchToMenu() throws Exception { 
        App.setRoot("menu-ordinary-differential-equations"); 
    }
}