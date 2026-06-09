package com.numeric.methods.controllers;

import java.io.IOException;
import java.util.List;
import com.numeric.methods.App;
import com.numeric.methods.logic.FalsePositionMethod;
import com.numeric.methods.logic.FalsePositionMethod.ResultRow; // Importación directa del POJO interno

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class falsePositionController {

    // Componentes visuales inyectados desde el FXML
    @FXML private TextField functionField;
    @FXML private TextField lowerLimitField;
    @FXML private TextField upperLimitField;
    @FXML private TextField iterationsField;
    @FXML private TextField toleranceField;

    @FXML private TableView<ResultRow> resultsTable;
    @FXML private TableColumn<ResultRow, Integer> iterationColumn;
    @FXML private TableColumn<ResultRow, String> x0Column;
    @FXML private TableColumn<ResultRow, String> x1Column;
    @FXML private TableColumn<ResultRow, String> xrColumn;
    @FXML private TableColumn<ResultRow, String> fx0Column;
    @FXML private TableColumn<ResultRow, String> fx1Column;
    @FXML private TableColumn<ResultRow, String> fxrColumn;
    @FXML private TableColumn<ResultRow, String> errorColumn;

    private final ObservableList<ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        
        iterationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIteration()));
        x0Column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getX0()));
        x1Column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getX1()));
        xrColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getXr()));
        fx0Column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFx0()));
        fx1Column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFx1()));
        fxrColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFxr()));
        errorColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getError()));
        resultsTable.setItems(tableData);
    }

    @FXML
    private void calculateFalsePosition() {
        tableData.clear();

        String function = functionField.getText();
        String lowerText = lowerLimitField.getText();
        String upperText = upperLimitField.getText();
        String iterationsText = iterationsField.getText();
        String errorText = toleranceField.getText();

        // Validaciones iniciales de interfaz
        if (function == null || function.trim().isEmpty() || lowerText == null || lowerText.trim().isEmpty() || upperText == null || upperText.trim().isEmpty()) {
            showErrorAlert("Debe rellenar los campos de la función y los límites del intervalo.");
            return;
        }

        if ((iterationsText == null || iterationsText.trim().isEmpty()) && (errorText == null || errorText.trim().isEmpty())) {
            showErrorAlert("Debe ingresar el número de iteraciones máximas o la tolerancia deseada.");
            return;
        }

        try {
            double x0 = Double.parseDouble(lowerText.trim());
            double x1 = Double.parseDouble(upperText.trim());
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
                    showErrorAlert("La tolerancia de error debe ser mayor que cero.");
                    return;
                }
                useTolerance = true;
            }

            // 1. Instanciamos el motor lógico inyectándole los parámetros crudos
            FalsePositionMethod method = new FalsePositionMethod(x0, x1, maxIterations, tolerance, function.trim());

            // Validaciones rápidas de raíces exactas en las paredes del intervalo inicial
            if (method.evaluateFunction(x0) == 0.0) {
                showInfoAlert(String.format("Raíz exacta encontrada directamente en el límite inferior: x = %.6f", x0));
                return;
            }
            if (method.evaluateFunction(x1) == 0.0) {
                showInfoAlert(String.format("Raíz exacta encontrada directamente en el límite superior: x = %.6f", x1));
                return;
            }

            // 2. Ejecutar y recibir el historial de renglones procesado enteramente por la lógica
            List<ResultRow> executionRows = method.generateResults(useTolerance);

            // 3. Volcar los resultados calculados a la visualización de la tabla
            tableData.addAll(executionRows);
            resultsTable.setItems(tableData);

            // Mostrar el alert emergente con la causa de finalización exacta (Tolerancia vs Máximo de ciclos)
            if (!executionRows.isEmpty()) {
                showInfoAlert(method.getExitReason());
            }

        } catch (NumberFormatException ex) {
            showErrorAlert("Los campos numéricos deben contener valores válidos.");
        } catch (IllegalArgumentException ex) {
            showErrorAlert(ex.getMessage()); // Captura violaciones de Bolzano desde la lógica
        } catch (Exception ex) {
            showErrorAlert("Ocurrió un error inesperado al procesar: " + ex.getMessage());
        }
    }

    @FXML private void switchToMenu() throws IOException { App.setRoot("menu-roots-aproximation"); }

    private void showErrorAlert(String message) {
        javafx.application.Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void showInfoAlert(String message) {
        javafx.application.Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}