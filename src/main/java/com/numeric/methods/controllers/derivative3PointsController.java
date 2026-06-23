package com.numeric.methods.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.numeric.methods.App;
import com.numeric.methods.logic.Derivative3Points;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;  
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class derivative3PointsController {

    // Componentes de la vista inyectados por FXML
    @FXML private ComboBox<String> methodComboBox;
    @FXML private TextField functionField;
    @FXML private TextField initialPointField;
    @FXML private TextField stepSizeField;
    @FXML private TextField coordinatesField;
    @FXML private TextField backwardField;
    @FXML private TextField centralField;
    @FXML private TextField forwardField;
    @FXML private TableView<Derivative3Points.ResultRow> resultTable;
    @FXML private TableColumn<Derivative3Points.ResultRow, Number> pointColumn;
    @FXML private TableColumn<Derivative3Points.ResultRow, Number> xColumn;
    @FXML private TableColumn<Derivative3Points.ResultRow, Number> fxColumn;
    @FXML private TableColumn<Derivative3Points.ResultRow, Number> ffxColumn;

    private final ObservableList<Derivative3Points.ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        methodComboBox.setItems(FXCollections.observableArrayList(
            "h igualmente espaciado",
            "h no igualmente espaciado"
        ));
        pointColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIteration()));
        xColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getXi()));
        fxColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getYi()));
        ffxColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getDerivative()));
        resultTable.setItems(tableData);

        methodComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        
        clearFields();
        if ("h igualmente espaciado".equals(newValue)) {
            functionField.setDisable(false);
            initialPointField.setDisable(false);
            stepSizeField.setDisable(false);
            coordinatesField.setDisable(true);
            coordinatesField.setPromptText("No se requiere para este método");
        } else if ("h no igualmente espaciado".equals(newValue)) {
            functionField.setDisable(true);
            initialPointField.setDisable(true);
            stepSizeField.setDisable(true);
            coordinatesField.setDisable(false);
            coordinatesField.setPromptText("Ej: 1.0, 5.2, 1.4, 7.8");
        }
        });
    }

    @FXML
    private void calculateDerivative3Points() {

    String selectedMethod = methodComboBox.getSelectionModel().getSelectedItem();
    String function = functionField.getText().trim();
    String initialP = initialPointField.getText().trim();
    String stepSize = stepSizeField.getText().trim();
    String coordinates = coordinatesField.getText().trim();
    
    if (selectedMethod == null) {
        showErrorAlert("Por favor, seleccione un método de derivación válido.");

    } else if (selectedMethod.equals("h igualmente espaciado")) {

        Derivative3Points derivative = new Derivative3Points(function, Double.parseDouble(initialP), Double.parseDouble(stepSize));

        double backward = derivative.calculateBackward();
        double central = derivative.calculateCentral();
        double forward = derivative.calculateForward();

        showInfoAlert(String.format("Resultados para h igualmente espaciado:\nDerivada hacia atrás: %.6f\nDerivada central: %.6f\nDerivada hacia adelante: %.6f", backward, central, forward));

        backwardField.setText(String.format("%.6f", backward));
        centralField.setText(String.format("%.6f", central));
        forwardField.setText(String.format("%.6f", forward));


    } else if (selectedMethod.equals("h no igualmente espaciado")) {

        Derivative3Points derivative = new Derivative3Points(parseArray(coordinates));

        List<Derivative3Points.ResultRow> results = derivative.generateTableData();
        tableData.setAll(results);
    }
    }

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu-numerical-differentiation-integration");
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

    private List<Derivative3Points.Point> parseArray(String coordinates) {
        if (coordinates == null || coordinates.trim().isEmpty()) {
            showErrorAlert("Por favor, ingrese coordenadas en el formato: x0, y0, x1, y1");
            return null;
        }
        
        String[] coords = coordinates.split(",");
        
        if (coords.length < 2) {
            showErrorAlert("Se requiere al menos una coordenada (x, y)");
            return null;
        }
        if (coords.length % 2 != 0) {
            showErrorAlert("El número de coordenadas debe ser par (pares x, y). Ingresó " + coords.length + " valores.");
            return null;
        }
        
        double[] values = new double[coords.length];
        
        for (int i = 0; i < coords.length; i++) {
            try {
                values[i] = Double.parseDouble(coords[i].trim());
            } catch (NumberFormatException e) {
                showErrorAlert("Valor inválido: '" + coords[i] + "'. Ingrese solo números.");
                return null;
            } catch (Exception e) {
                showErrorAlert("Ocurrió un error al procesar las coordenadas: " + e.getMessage());
                return null;
            }
        }
        
        List<Derivative3Points.Point> points = new ArrayList<>();
        for (int i = 0; i < values.length; i += 2) {
            points.add(new Derivative3Points.Point(values[i], values[i + 1]));
        }
        
        return points;
    }

    private void clearFields() {
        backwardField.clear();
        centralField.clear();
        forwardField.clear();
        tableData.clear();
    }
}
