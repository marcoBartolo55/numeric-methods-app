package com.numeric.methods.controllers;

import com.numeric.methods.App;
import com.numeric.methods.logic.DividedDifferences;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

public class dividedDifferencesController {

    @FXML private TextField pointsCountField, evalXField, polynomialOutputField, interpolatedValueField;
    @FXML private TableView<PointData> captureTable;
    @FXML private TableColumn<PointData, Double> xCaptureColumn, yCaptureColumn;
    @FXML private TableView<DividedDifferences.ResultRow> pyramidResultsTable;

    public static class PointData {
        public double x, y;
        public PointData(double x, double y) { this.x = x; this.y = y; }
    }

    @FXML
    public void initialize() {
        xCaptureColumn.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().x));
        yCaptureColumn.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().y));
        
        // Habilitar edición real
        xCaptureColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        xCaptureColumn.setOnEditCommit(e -> e.getRowValue().x = e.getNewValue());
        
        yCaptureColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        yCaptureColumn.setOnEditCommit(e -> e.getRowValue().y = e.getNewValue());
        
        captureTable.setEditable(true);
    }

    @FXML
    private void handleGenerateTable() {
        int n = Integer.parseInt(pointsCountField.getText());
        ObservableList<PointData> data = FXCollections.observableArrayList();
        for (int i = 0; i < n; i++) data.add(new PointData(0.0, 0.0));
        captureTable.setItems(data);
    }

    @FXML
    private void calculateDividedDifferences() {
        int n = captureTable.getItems().size();
        double[] x = new double[n], y = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = captureTable.getItems().get(i).x;
            y[i] = captureTable.getItems().get(i).y;
        }

        DividedDifferences solver = new DividedDifferences(x, y);
        double[][] table = solver.computeTable();
        
        pyramidResultsTable.getColumns().clear();
        for (int j = 0; j < n; j++) {
            final int col = j;
            TableColumn<DividedDifferences.ResultRow, Double> c = new TableColumn<>("f[" + j + "]");
            c.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getDiff(col)));
            pyramidResultsTable.getColumns().add(c);
        }
        pyramidResultsTable.setItems(FXCollections.observableArrayList(solver.generateRows(table)));
        
        double evalX = Double.parseDouble(evalXField.getText());
        interpolatedValueField.setText(String.format("%.6f", evaluateNewton(x, table, evalX)));
    }

    private double evaluateNewton(double[] x, double[][] table, double xTarget) {
        double res = table[0][0], term = 1;
        for (int i = 1; i < x.length; i++) {
            term *= (xTarget - x[i - 1]);
            res += table[i][i] * term;
        }
        return res;
    }

    @FXML
    private void switchToMenu() throws Exception { App.setRoot("menu-numerical-differentiation-integration"); }
}