package com.numeric.methods.controllers;

import com.numeric.methods.App;
import com.numeric.methods.logic.Derivative2Points;
import com.numeric.methods.logic.Derivative2Points.Point;
import com.numeric.methods.logic.Derivative2Points.DerivativeRow;
import com.numeric.methods.logic.Derivative3Points;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;

public class derivative3PointsController {

    // Componentes de la interfaz
    @FXML private ComboBox<String> spacingComboBox;
    @FXML private GridPane equalSpacingPane, unequalSpacingPane;
    @FXML private TextField functionField, initialPointField, stepSizeField, coordinatesField;
    @FXML private Button calculateButton;
    
    // Componentes de la tabla (Añadimos point3Column)
    @FXML private TableView<DerivativeRow> resultTable;
    @FXML private TableColumn<DerivativeRow, String> rowLabelColumn, point1Column, point2Column, point3Column;

    private final ObservableList<DerivativeRow> tableData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Configurar columnas de la tabla
        rowLabelColumn.setCellValueFactory(d -> d.getValue().variableProperty());
        point1Column.setCellValueFactory(d -> d.getValue().puntoProperty(0));
        point2Column.setCellValueFactory(d -> d.getValue().puntoProperty(1));
        point3Column.setCellValueFactory(d -> d.getValue().puntoProperty(2)); // Tercer punto
        resultTable.setItems(tableData);

        // Lógica para mostrar/ocultar paneles
        spacingComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            calculateButton.setDisable(false);
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
    private void calculateDerivative() {
        try {
            String selectedMethod = spacingComboBox.getValue();
            List<Point> points;

            if ("h igualmente espaciada".equals(selectedMethod)) {
                String func = functionField.getText();
                if (func == null || func.trim().isEmpty()) {
                    throw new Exception("Ingrese la función f(x).");
                }
                
                double x0 = Double.parseDouble(initialPointField.getText());
                double h = Double.parseDouble(stepSizeField.getText());
                if (h == 0) throw new Exception("El tamaño de paso (h) no puede ser cero.");

                // Generamos exactamente 3 puntos usando el método reciclado de 2 puntos
                points = Derivative2Points.generatePointsEquallySpaced(func, x0, h, 3);

            } else {
                String input = coordinatesField.getText();
                if (input == null || input.trim().isEmpty()) throw new Exception("El campo de coordenadas está vacío.");
                
                points = parseCoordinates(input);
                if (points.size() != 3) {
                    throw new Exception("Se requieren exactamente 3 puntos (6 valores: x0, y0, x1, y1, x2, y2).");
                }
            }

            fillTable(points);

        } catch (NumberFormatException e) {
            showErrorAlert("Ingrese valores numéricos válidos en los campos de texto.");
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    private void fillTable(List<Point> points) {
        tableData.clear();
        DerivativeRow rowX = new DerivativeRow("x", 3);
        DerivativeRow rowY = new DerivativeRow("f(x)", 3);
        DerivativeRow rowD = new DerivativeRow("f'(x)", 3);

        for (int i = 0; i < points.size(); i++) {
            // Llenar x y f(x)
            rowX.setPunto(i, String.format("%.4f", points.get(i).getX()));
            rowY.setPunto(i, String.format("%.4f", points.get(i).getY()));
            
            // Calcular la derivada para cada punto (x0, x1, x2)
            double der = Derivative3Points.calculateDerivative(points, points.get(i).getX());
            rowD.setPunto(i, String.format("%.4f", der));
        }

        tableData.addAll(rowX, rowY, rowD);
    }

    private List<Point> parseCoordinates(String input) throws Exception {
        String[] parts = input.split(",");
        if (parts.length % 2 != 0) throw new Exception("El número de coordenadas debe ser par.");

        List<Point> list = new ArrayList<>();
        for (int i = 0; i < parts.length; i += 2) {
            try {
                list.add(new Point(Double.parseDouble(parts[i].trim()), Double.parseDouble(parts[i+1].trim())));
            } catch (NumberFormatException e) {
                throw new Exception("Valor numérico inválido en las coordenadas.");
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