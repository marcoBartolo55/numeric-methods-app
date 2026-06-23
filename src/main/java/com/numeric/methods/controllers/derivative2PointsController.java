package com.numeric.methods.controllers;

import com.numeric.methods.App;
import com.numeric.methods.logic.Derivative2Points;
import com.numeric.methods.logic.Derivative2Points.Point;
import com.numeric.methods.logic.Derivative2Points.DerivativeRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;

public class derivative2PointsController {

    // Componentes de la interfaz
    @FXML private ComboBox<String> spacingComboBox;
    @FXML private GridPane equalSpacingPane, unequalSpacingPane;
    @FXML private TextField functionField, initialPointField, stepSizeField, coordinatesField;
    @FXML private Button calculateButton;
    
    // Componentes de la tabla
    @FXML private TableView<DerivativeRow> resultTable;
    @FXML private TableColumn<DerivativeRow, String> rowLabelColumn, point1Column, point2Column;

    private final ObservableList<DerivativeRow> tableData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Configurar columnas de la tabla
        rowLabelColumn.setCellValueFactory(d -> d.getValue().variableProperty());
        point1Column.setCellValueFactory(d -> d.getValue().puntoProperty(0));
        point2Column.setCellValueFactory(d -> d.getValue().puntoProperty(1));
        resultTable.setItems(tableData);

        // Lógica dinámica para mostrar/ocultar paneles según el ComboBox
        spacingComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            calculateButton.setDisable(false); // Habilitar el botón

            if ("h igualmente espaciada".equals(newValue)) {
                equalSpacingPane.setVisible(true);
                equalSpacingPane.setManaged(true);
                unequalSpacingPane.setVisible(false);
                unequalSpacingPane.setManaged(false);
            } else if ("h no igualmente espaciada".equals(newValue)) {
                unequalSpacingPane.setVisible(true);
                unequalSpacingPane.setManaged(true);
                equalSpacingPane.setVisible(false);
                equalSpacingPane.setManaged(false);
            }
        });
    }

    @FXML
    private void calculateDerivative() { // Ojo: en el FXML lo llamamos calculateDerivative
        try {
            String selectedMethod = spacingComboBox.getValue();
            List<Point> points;

            if ("h igualmente espaciada".equals(selectedMethod)) {
                // Modo 1: Evaluando f(x)
                String func = functionField.getText();
                if (func == null || func.trim().isEmpty()) {
                    throw new Exception("Ingrese la función f(x).");
                }
                
                double x0 = Double.parseDouble(initialPointField.getText());
                double h = Double.parseDouble(stepSizeField.getText());
                
                if (h == 0) throw new Exception("El tamaño de paso (h) no puede ser cero.");

                // Generamos exactamente 2 puntos (x0 y x0+h)
                points = Derivative2Points.generatePointsEquallySpaced(func, x0, h, 2);

            } else {
                // Modo 2: Coordenadas manuales
                String input = coordinatesField.getText();
                if (input == null || input.trim().isEmpty()) {
                    throw new Exception("El campo de coordenadas está vacío.");
                }
                
                points = parseCoordinates(input);
                if (points.size() != 2) {
                    throw new Exception("Se requieren exactamente 2 puntos (4 valores: x0, y0, x1, y1).");
                }
            }

            // Dibujar la tabla con los puntos resultantes
            fillTable(points);

        } catch (NumberFormatException e) {
            showErrorAlert("Ingrese valores numéricos válidos en los campos de Punto Inicial o Tamaño de Paso.");
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    private void fillTable(List<Point> points) {
        tableData.clear();
        DerivativeRow rowX = new DerivativeRow("x", 2);
        DerivativeRow rowY = new DerivativeRow("f(x)", 2);
        DerivativeRow rowD = new DerivativeRow("f'(x)", 2);

        // Llenar filas de x y f(x)
        for (int i = 0; i < points.size(); i++) {
            rowX.setPunto(i, String.format("%.4f", points.get(i).getX()));
            rowY.setPunto(i, String.format("%.4f", points.get(i).getY()));
        }

        // Calcular derivada e insertarla en el segundo punto
        double der = Derivative2Points.calculateDerivative(points.get(0), points.get(1));
        rowD.setPunto(1, String.format("%.4f", der));

        tableData.addAll(rowX, rowY, rowD);
    }

    private List<Point> parseCoordinates(String input) throws Exception {
        String[] parts = input.split(",");
        if (parts.length % 2 != 0) {
            throw new Exception("El número de coordenadas debe ser par (pares x, y).");
        }

        List<Point> list = new ArrayList<>();
        for (int i = 0; i < parts.length; i += 2) {
            try {
                list.add(new Point(Double.parseDouble(parts[i].trim()), 
                                   Double.parseDouble(parts[i+1].trim())));
            } catch (NumberFormatException e) {
                throw new Exception("Valor inválido en las coordenadas. Ingrese solo números separados por comas.");
            }
        }
        return list;
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de entrada");
        alert.setHeaderText("Entrada no válida");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void switchToMenu() throws Exception { 
        App.setRoot("menu-numerical-differentiation-integration"); 
    }
}