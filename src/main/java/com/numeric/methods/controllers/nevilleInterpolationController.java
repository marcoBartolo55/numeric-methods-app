package com.numeric.methods.controllers;

import com.numeric.methods.App;
import com.numeric.methods.logic.NevilleInterpolation;
import com.numeric.methods.logic.NevilleInterpolation.ResultRow;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class nevilleInterpolationController {
    @FXML private TextField xInput, yInput, targetXField, finalResultField; 
    @FXML private TableView<ResultRow> resultTable;

    @FXML
    public void calculateNeville() {
        try {
            double[] x = parseInput(xInput.getText());
            double[] y = parseInput(yInput.getText());
            double target = Double.parseDouble(targetXField.getText());

            NevilleInterpolation solver = new NevilleInterpolation(x, y);
            var data = solver.generateTableData(target);

            resultTable.getColumns().clear();
            
            TableColumn<ResultRow, Double> xCol = new TableColumn<>("x_i");
            xCol.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getX()));
            resultTable.getColumns().add(xCol);

            for (int j = 0; j < x.length; j++) {
                final int colIndex = j;
                TableColumn<ResultRow, Double> pCol = new TableColumn<>("P_i," + j);
                pCol.setCellValueFactory(d -> {
                    Double val = d.getValue().getP(colIndex);
                    return new SimpleObjectProperty<>(val == 0.0 && colIndex > 0 ? null : val);
                });
                resultTable.getColumns().add(pCol);
            }

            resultTable.setItems(FXCollections.observableArrayList(data));
            
            double res = data.get(data.size() - 1).getP(x.length - 1);
            finalResultField.setText(String.format("%.6f", res));

        } catch (Exception e) {
            finalResultField.setText("Error");
            System.err.println("Error: " + e.getMessage());
        }
    }

    @FXML
    private void switchToMenu() throws Exception {
        App.setRoot("menu-numerical-differentiation-integration");
    }

    private double[] parseInput(String input) {
        String[] parts = input.split(",");
        double[] arr = new double[parts.length];
        for(int i = 0; i < parts.length; i++) arr[i] = Double.parseDouble(parts[i].trim());
        return arr;
    }
}