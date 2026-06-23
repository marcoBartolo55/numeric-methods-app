package com.numeric.methods.controllers;

import com.numeric.methods.logic.NewtonRaphsonMethod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.math3.complex.Complex;

public class newtonRaphsonController {

    @FXML private TextField realField, imagField, iterationsField, errorField;
    @FXML private TableView<NewtonRaphsonMethod.ResultRow> resultsTable;
    @FXML private TableColumn<NewtonRaphsonMethod.ResultRow, Integer> iterationColumn;
    @FXML private TableColumn<NewtonRaphsonMethod.ResultRow, String> pColumn, fpnColumn, ffpnColumn, pn1Column, errorColumn;

    private final ObservableList<NewtonRaphsonMethod.ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        iterationColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getIteration()));
        pColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getP()));
        fpnColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getFpn()));
        ffpnColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getFfpn()));
        pn1Column.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getPn1()));
        errorColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getError()));
        resultsTable.setItems(tableData);
    }

    @FXML
    private void calculateNewton() {
        try {
            double real = Double.parseDouble(realField.getText());
            double imag = Double.parseDouble(imagField.getText());
            int iter = Integer.parseInt(iterationsField.getText());
            double err = Double.parseDouble(errorField.getText());

            NewtonRaphsonMethod method = new NewtonRaphsonMethod(new Complex(real, imag), iter, err);
            tableData.setAll(method.execute(!errorField.getText().isEmpty()));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Datos inválidos: " + e.getMessage()).show();
        }
    }
}