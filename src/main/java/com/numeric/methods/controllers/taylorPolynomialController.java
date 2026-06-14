package com.numeric.methods.controllers;

import com.numeric.methods.App;
import com.numeric.methods.logic.TaylorPolynomial;
import com.numeric.methods.logic.TaylorPolynomial.ResultRow;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class taylorPolynomialController {

    @FXML private TextField functionField, expansionPointField, targetXField, orderField;
    @FXML private TableView<ResultRow> resultsTable;
    
    // Columnas de la tabla (asegúrate de que los fx:id coincidan en tu FXML)
    @FXML private TableColumn<ResultRow, Integer> nColumn;
    @FXML private TableColumn<ResultRow, Double> termColumn;
    @FXML private TableColumn<ResultRow, Double> sumColumn;

    @FXML
    public void initialize() {
        // Mapeo de columnas con los métodos definidos en ResultRow
        nColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getN()).asObject());
        termColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getTermValue()).asObject());
        sumColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPartialSum()).asObject());
    }

    @FXML
    public void calculateTaylor() {
        try {
            String function = functionField.getText().trim();
            double a = Double.parseDouble(expansionPointField.getText().trim());
            double x = Double.parseDouble(targetXField.getText().trim());
            int n = Integer.parseInt(orderField.getText().trim());

            TaylorPolynomial solver = new TaylorPolynomial(function, a);
            ObservableList<ResultRow> data = FXCollections.observableArrayList(solver.generateTableData(x, n));
            
            resultsTable.setItems(data);

        } catch (NumberFormatException e) {
            System.err.println("Error en el formato de los números.");
        } catch (Exception e) {
            System.err.println("Error al calcular Taylor: " + e.getMessage());
        }
    }

    @FXML
    private void switchToMenu() throws Exception {
        App.setRoot("menu-numerical-differentiation-integration");
    }
}