package com.numeric.methods.controllers;

import com.numeric.methods.App;
import com.numeric.methods.logic.MultipleSimpson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class multipleSimpsonController {

    @FXML private TextField functionField, initialPointField, finalPointField, segmentsField, resultField;
    @FXML private ComboBox<String> methodSelector;
    @FXML private TableView<MultipleSimpson.ResultRow> resultsTable;
    @FXML private TableColumn<MultipleSimpson.ResultRow, Integer> iterationColumn, weightColumn;
    @FXML private TableColumn<MultipleSimpson.ResultRow, Double> xiColumn, fxiColumn, contributionColumn;

    private final ObservableList<MultipleSimpson.ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        methodSelector.getItems().addAll("Simpson 1/3", "Simpson 3/8");
        methodSelector.setValue("Simpson 1/3");
        iterationColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getIteration()));
        xiColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getXi()));
        fxiColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getFxi()));
        weightColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getWeight()));
        contributionColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getContribution()));
        resultsTable.setItems(tableData);
    }

    @FXML
    private void calculateMultipleSimpson() {
        try {
            double a = Double.parseDouble(initialPointField.getText());
            double b = Double.parseDouble(finalPointField.getText());
            int n = Integer.parseInt(segmentsField.getText());
            boolean is38 = methodSelector.getValue().equals("Simpson 3/8");

            if (is38 && n % 3 != 0) {
                showErrorAlert("Para Simpson 3/8, el número de segmentos debe ser múltiplo de 3.");
                return;
            } else if (!is38 && n % 2 != 0) {
                showErrorAlert("Para Simpson 1/3, el número de segmentos debe ser par.");
                return;
            }

            MultipleSimpson solver = new MultipleSimpson(functionField.getText(), a, b, n);
            double res = is38 ? solver.calculateIntegral38() : solver.calculateIntegral13();
            
            resultField.setText(String.format("%.6f", res));
            tableData.setAll(solver.generateTableData(is38));

        } catch (Exception e) {
            showErrorAlert("Error en los datos de entrada.");
        }
    }

    @FXML
    private void switchToMenu() throws IOException { App.setRoot("menu-ordinary-differential-equations"); }

    private void showErrorAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
    }
}