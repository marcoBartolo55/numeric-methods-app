package com.numeric.methods.controllers;

import java.io.IOException;
import java.util.List;
import com.numeric.methods.App;
import com.numeric.methods.logic.FixPointMethod;
import com.numeric.methods.logic.FixPointMethod.ResultRow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class fixPointController {

    // Componentes visuales inyectados desde el FXML
    @FXML private TextField functionField;
    @FXML private TextField initialPointField;
    @FXML private TextField iterationsField;
    @FXML private TextField errorField;

    @FXML private TableView<ResultRow> resultsTable;
    @FXML private TableColumn<ResultRow, Integer> iterationColumn;
    @FXML private TableColumn<ResultRow, Number> x0Column;
    @FXML private TableColumn<ResultRow, Number> gxnColumn;
    @FXML private TableColumn<ResultRow, Number> errorColumn;

    private final ObservableList<ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        iterationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIteration()));
        x0Column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getX0()));
        gxnColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getGxn()));
        errorColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getError()));
        resultsTable.setItems(tableData);
    }

    @FXML
    private void calculateFixPoint() {
        tableData.clear();

        String function = functionField.getText();
        String initialPoint = initialPointField.getText();
        String iteration = iterationsField.getText();
        String error = errorField.getText();

    // Validaciones en la interfaz
        if (function.isEmpty() || initialPoint.isEmpty()) {
            showErrorAlert("Debe rellenar los campos de la función y los límites del intervalo.");
            return;
        }

        if ((iteration.isEmpty() && error.isEmpty()) || (!iteration.isEmpty() && !error.isEmpty())) {
            showErrorAlert("Debe rellenar solo uno de los campos: Iteraciones o Error.");
            return;
        }

        FixPointMethod method = new FixPointMethod(Double.parseDouble(initialPoint), iteration.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(iteration), error.isEmpty() ? Double.MIN_VALUE : Double.parseDouble(error), function);
        List<ResultRow> results = method.generateResults(error.isEmpty() ? false : true);
        tableData.addAll(results);
        showInfoAlert(method.getExitReason());
    }

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu-roots-aproximation");
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Validación");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
