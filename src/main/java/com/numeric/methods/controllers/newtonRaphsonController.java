package com.numeric.methods.controllers;

import java.io.IOException;
import java.util.List;
import com.numeric.methods.App;
import com.numeric.methods.logic.NewtonRaphsonMethod;
import com.numeric.methods.logic.NewtonRaphsonMethod.ResultRow; // Importación limpia

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class newtonRaphsonController {

    @FXML private TextField functionField;
    @FXML private TextField initialPointField;
    @FXML private TextField iterationsField;
    @FXML private TextField errorField;

    @FXML private TableView<ResultRow> resultsTable;
    @FXML private TableColumn<ResultRow, Integer> iterationColumn;
    @FXML private TableColumn<ResultRow, String> pColumn;
    @FXML private TableColumn<ResultRow, String> fpnColumn;
    @FXML private TableColumn<ResultRow, String> ffpnColumn;
    @FXML private TableColumn<ResultRow, String> pnColumn;
    @FXML private TableColumn<ResultRow, String> errorColumn;

    private final ObservableList<ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        iterationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIteration()));
        pColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getP()));
        fpnColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFpn()));
        ffpnColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFfpn()));
        pnColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPn1()));
        errorColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getError()));
        resultsTable.setItems(tableData);
    }

    @FXML
    private void calculateNewtonRaphson() {
        tableData.clear();

        String function = functionField.getText();
        String initialPointText = initialPointField.getText();
        String iterationsText = iterationsField.getText();
        String errorText = errorField.getText();

        if (function == null || function.trim().isEmpty() || initialPointText == null || initialPointText.trim().isEmpty()) {
            showErrorAlert("Debe ingresar la función y el punto inicial.");
            return;
        }

        if ((iterationsText == null || iterationsText.trim().isEmpty()) && (errorText == null || errorText.trim().isEmpty())) {
            showErrorAlert("Debe ingresar número de iteraciones o tolerancia.");
            return;
        }

        try {
            double currentP = Double.parseDouble(initialPointText.trim());
            int maxIterations = 100;
            double tolerance = 0.0;
            boolean useTolerance = false;

            if (iterationsText != null && !iterationsText.trim().isEmpty()) {
                maxIterations = Integer.parseInt(iterationsText.trim());
                if (maxIterations <= 0) {
                    showErrorAlert("El número de iteraciones debe ser mayor que cero.");
                    return;
                }
            } else {
                tolerance = Double.parseDouble(errorText.trim());
                if (tolerance <= 0) {
                    showErrorAlert("La tolerancia debe ser mayor que cero.");
                    return;
                }
                useTolerance = true;
            }

            // 1. Instanciar la lógica
            NewtonRaphsonMethod method = new NewtonRaphsonMethod(currentP, maxIterations, tolerance, function.trim());

            // 2. Ejecutar y obtener las filas calculadas en la lógica
            List<ResultRow> executionRows = method.execute(useTolerance);

            // 3. Poblar la vista
            tableData.addAll(executionRows);
            resultsTable.setItems(tableData);

            // Alerta informativa sobre la razón de término (Tolerancia o Máximo de vueltas)
            if (!executionRows.isEmpty()) {
                showInfoAlert(method.getExitReason());
            }

        } catch (NumberFormatException ex) {
            showErrorAlert("Los campos numéricos deben contener valores válidos.");
        } catch (IllegalArgumentException ex) {
            showErrorAlert(ex.getMessage()); // Muestra alertas de divergencia lanzadas desde la lógica
        } catch (Exception ex) {
            showErrorAlert("Ocurrió un error inesperado: " + ex.getMessage());
        }
    }

    @FXML private void switchToMain() throws IOException { App.setRoot("main"); }
    @FXML private void switchToMenu() throws IOException { App.setRoot("menu-roots-aproximation"); }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}