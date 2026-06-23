package com.numeric.methods.controllers;

import com.numeric.methods.App;
import com.numeric.methods.logic.FixPointMethod;
import com.numeric.methods.logic.FixPointMethod.ResultRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.math3.complex.Complex;
import java.io.IOException;

public class fixPointController {

    // IDs exactos que coinciden con tu nuevo FXML
    @FXML private TextField functionField, initialPointField, iterationsField, errorField;
    @FXML private TableView<ResultRow> resultsTable;
    @FXML private TableColumn<ResultRow, Integer> iterationColumn;
    @FXML private TableColumn<ResultRow, String> x0Column, gxnColumn;
    @FXML private TableColumn<ResultRow, Number> errorColumn;

    private final ObservableList<ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        iterationColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getIteration()));
        x0Column.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getX0()));
        gxnColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getGxn()));
        errorColumn.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getError()));
        resultsTable.setItems(tableData);
    }

    @FXML
    private void calculateFixPoint() {
        try {
            // Aquí leemos de los campos correctos
            double initial = Double.parseDouble(initialPointField.getText());
            int iter = iterationsField.getText().isEmpty() ? 100 : Integer.parseInt(iterationsField.getText());
            double err = errorField.getText().isEmpty() ? 0.0 : Double.parseDouble(errorField.getText());

            // Usamos Complex(real, 0) porque el campo inicial es un solo valor
            FixPointMethod method = new FixPointMethod(new Complex(initial, 0), iter, err);
            
            // Nota: Aquí se está ejecutando la lógica hardcodeada en FixPointMethod.java
            tableData.setAll(method.generateResults(!errorField.getText().isEmpty()));
            
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error en los datos: " + e.getMessage()).show();
        }
    }

    @FXML
    private void switchToMenu() throws IOException { App.setRoot("menu-roots-aproximation"); }
}