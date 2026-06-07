package com.numeric.methods.controllers;

import java.io.IOException;
import java.util.List;
import com.numeric.methods.App;
import com.numeric.methods.logic.BisectionMethod;
import com.numeric.methods.logic.BisectionMethod.ResultRow; // Importación directa del POJO interno

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class bisectionController {

    // Componentes de la vista inyectados por FXML
    @FXML private TextField functionField;
    @FXML private TextField lowerLimitField;
    @FXML private TextField upperLimitField;
    @FXML private TextField iterationsField;
    @FXML private TextField errorField;

    @FXML private TableView<ResultRow> resultsTable;
    @FXML private TableColumn<ResultRow, Integer> iterationColumn;
    @FXML private TableColumn<ResultRow, String> aColumn;
    @FXML private TableColumn<ResultRow, String> bColumn;
    @FXML private TableColumn<ResultRow, String> cColumn;
    @FXML private TableColumn<ResultRow, String> aproxColumn;
    @FXML private TableColumn<ResultRow, String> faColumn;
    @FXML private TableColumn<ResultRow, String> fbColumn;
    @FXML private TableColumn<ResultRow, String> fcColumn;
    @FXML private TableColumn<ResultRow, String> errorColumn;

    // Lista observable mapeada a la tabla
    private ObservableList<ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        
        iterationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIteration()));
        aColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getA()));
        bColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getB()));
        cColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAproximacion()));
        aproxColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAproximacion()));
        faColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFa()));
        fbColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFb()));
        fcColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFc()));
        errorColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getError()));
        resultsTable.setItems(tableData);
    }

    @FXML
    private void calculateBisection() {
        tableData.clear();
        
        try {
            
            double a = Double.parseDouble(lowerLimitField.getText());
            double b = Double.parseDouble(upperLimitField.getText());
            String function = functionField.getText();
            
            int maxIterations = 0;
            double tolerance = 0;
            boolean useIterations = false;

            
            if (!iterationsField.getText().isEmpty()) {
                maxIterations = Integer.parseInt(iterationsField.getText());
                useIterations = true;
            } else if (!errorField.getText().isEmpty()) {
                tolerance = Double.parseDouble(errorField.getText());
                useIterations = false;
            } else {
                showErrorAlert("Debe ingresar número de iteraciones o tolerancia máxima.");
                return;
            }

            
            BisectionMethod method = new BisectionMethod(a, b, maxIterations, tolerance, function);

            
            if (method.evaluateFunction(a) == 0.0) {
                showInfoAlert(String.format("Raíz exacta encontrada en el límite inferior: x = %.6f", a));
                return;
            }
            if (method.evaluateFunction(b) == 0.0) {
                showInfoAlert(String.format("Raíz exacta encontrada en el límite superior: x = %.6f", b));
                return;
            }

            
            List<ResultRow> executionRows = method.generateResults(useIterations);
            
            
            tableData.addAll(executionRows);
            resultsTable.setItems(tableData);

            
            if (!executionRows.isEmpty()) {
                showInfoAlert(method.getExitReason());
            }

        } catch (NumberFormatException ex) {
            showErrorAlert("Por favor, introduce valores numéricos coherentes.");
        } catch (IllegalArgumentException ex) {
            
            showErrorAlert(ex.getMessage());
        }
    }

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu-roots-aproximation");
    }

    private void showErrorAlert(String message) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void showInfoAlert(String message) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}