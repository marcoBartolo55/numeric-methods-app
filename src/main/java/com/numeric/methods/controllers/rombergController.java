package com.numeric.methods.controllers;

import com.numeric.methods.App;
import com.numeric.methods.logic.Romberg;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class rombergController {
    @FXML private TextField functionField, aField, bField, nField;
    @FXML private TableView<double[]> resultsTable;

    @FXML
    public void calculateRomberg() {
        try {
            String f = functionField.getText();
            double a = Double.parseDouble(aField.getText());
            double b = Double.parseDouble(bField.getText());
            int n = Integer.parseInt(nField.getText());

            Romberg solver = new Romberg(f);
            double[][] data = solver.computeRomberg(a, b, n);

            resultsTable.getColumns().clear();
            for (int j = 0; j < n; j++) {
                final int col = j;
                TableColumn<double[], String> c = new TableColumn<>("R(i," + j + ")");
                c.setCellValueFactory(d -> new SimpleObjectProperty<>(
                    (col < d.getValue().length && d.getValue()[col] != 0) 
                    ? String.format("%.8f", d.getValue()[col]) : ""
                ));
                resultsTable.getColumns().add(c);
            }
            resultsTable.setItems(FXCollections.observableArrayList(data));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @FXML
    private void switchToMenu() throws Exception {
        App.setRoot("menu-numerical-differentiation-integration");
    }
}