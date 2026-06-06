package com.numeric.methods.controllers;

import java.io.IOException;

import com.numeric.methods.App;
import com.numeric.methods.logic.NewtonRaphsonMethod;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class newtonRaphsonController {

    @FXML
    private TextField functionField;

    @FXML
    private TextField initialPointField;

    @FXML
    private TextField iterationsField;

    @FXML
    private TextField errorField;

    @FXML
    private TableView<ResultRow> resultsTable;

    @FXML
    private TableColumn<ResultRow, Integer> iterationColumn;

    @FXML
    private TableColumn<ResultRow, String> pColumn;

    @FXML
    private TableColumn<ResultRow, String> fpnColumn;

    @FXML
    private TableColumn<ResultRow, String> ffpnColumn;

    @FXML
    private TableColumn<ResultRow, String> pnColumn;

    @FXML
    private TableColumn<ResultRow, String> errorColumn;

    private final ObservableList<ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        iterationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIteration()));
        pColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getP()));
        fpnColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFpn()));
        ffpnColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFfpn()));
        pnColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPn1()));
        errorColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getError()));
        resultsTable.setItems(tableData);
    }

    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("main");
    }

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu-roots-aproximation");
    }

    @FXML
    private void calculateNewtonRaphson() {
        tableData.clear();

        String function = functionField.getText();
        String initialPointText = initialPointField.getText();
        String iterationsText = iterationsField.getText();
        String errorText = errorField.getText();

        if (function == null || function.trim().isEmpty()) {
            showErrorAlert("Debe ingresar una funcion.");
            return;
        }

        if (initialPointText == null || initialPointText.trim().isEmpty()) {
            showErrorAlert("Debe ingresar un punto inicial.");
            return;
        }

        if ((iterationsText == null || iterationsText.trim().isEmpty()) &&
            (errorText == null || errorText.trim().isEmpty())) {
            showErrorAlert("Debe ingresar numero de iteraciones o tolerancia.");
            return;
        }

        try {
            double currentP = Double.parseDouble(initialPointText.trim());
            int maxIterations = 100;
            double tolerance = 0.0;
            boolean useTolerance = false;

            if (iterationsText != null && !iterationsText.trim().isEmpty()) {
                maxIterations = Integer.parseInt(iterationsText.trim());
                if (maxIterations <= 0) {
                    showErrorAlert("El numero de iteraciones debe ser mayor que cero.");
                    return;
                }
            } else {
                tolerance = Double.parseDouble(errorText.trim());
                if (tolerance <= 0) {
                    showErrorAlert("La tolerancia debe ser mayor que cero.");
                    return;
                }
                useTolerance = true;
            }

            NewtonRaphsonMethod method = new NewtonRaphsonMethod(currentP, maxIterations, tolerance, function.trim());

            for (int iteration = 1; iteration <= maxIterations; iteration++) {
                double fpn = method.evaluateFunction(currentP);

                if (Math.abs(fpn) < 1e-12) {
                    tableData.add(new ResultRow(
                        iteration,
                        formatValue(currentP),
                        formatValue(fpn),
                        "---",
                        formatValue(currentP),
                        "0.000000"
                    ));
                    showInfoAlert(String.format("Se encontro una raiz exacta en x = %.6f", currentP));
                    break;
                }

                double ffpn = method.evaluateDerivative(currentP);
                if (Math.abs(ffpn) < 1e-12) {
                    showErrorAlert(String.format("La derivada es cercana a cero en x = %.6f. El metodo no puede continuar.", currentP));
                    return;
                }

                double nextP = method.calculateNextP(currentP);
                double error = Math.abs(nextP - currentP);

                tableData.add(new ResultRow(
                    iteration,
                    formatValue(currentP),
                    formatValue(fpn),
                    formatValue(ffpn),
                    formatValue(nextP),
                    formatValue(error)
                ));

                if (error == 0.0 || (useTolerance && error <= tolerance)) {
                    if (useTolerance) {
                        showInfoAlert(String.format("Se alcanzo la tolerancia configurada: %.6f", tolerance));
                    }
                    break;
                }

                currentP = nextP;
            }

            resultsTable.setItems(tableData);
        } catch (NumberFormatException ex) {
            showErrorAlert("Los campos numericos deben contener valores validos.");
        } catch (IllegalArgumentException ex) {
            showErrorAlert(ex.getMessage());
        } catch (Exception ex) {
            showErrorAlert("Ocurrio un error inesperado: " + ex.getMessage());
        }
    }

    private String formatValue(double value) {
        return String.format("%.6f", value);
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfoAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacion");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class ResultRow {
        private final Integer iteration;
        private final String p;
        private final String fpn;
        private final String ffpn;
        private final String pn1;
        private final String error;

        public ResultRow(int iteration, String p, String fpn, String ffpn, String pn1, String error) {
            this.iteration = iteration;
            this.p = p;
            this.fpn = fpn;
            this.ffpn = ffpn;
            this.pn1 = pn1;
            this.error = error;
        }

        public Integer getIteration() {
            return iteration;
        }

        public String getP() {
            return p;
        }

        public String getFpn() {
            return fpn;
        }

        public String getFfpn() {
            return ffpn;
        }

        public String getPn1() {
            return pn1;
        }

        public String getError() {
            return error;
        }
    }

}
