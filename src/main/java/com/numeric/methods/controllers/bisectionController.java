package com.numeric.methods.controllers;

import java.io.IOException;
import com.numeric.methods.App;
import com.numeric.methods.logic.BisectionMethod;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class bisectionController {

    @FXML
    public void initialize() {
        iterationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIteration()));
        aColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getA()));
        bColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getB()));
        aproxColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAproximacion()));
        faColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFa()));
        fbColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFb()));
        fcColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFc()));
        errorColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getError()));
        aproxColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAproximacion()));
        resultsTable.setItems(tableData);
    }

    // Campos FXML de tipo TextField
    @FXML
    private TextField functionField;
    @FXML
    private TextField lowerLimitField;
    @FXML
    private TextField upperLimitField;

    // Campos FXML de tipo TableView y TableColumn
    @FXML
    private TableView<ResultRow> resultsTable;
    @FXML
    private TableColumn<ResultRow, Integer> iterationColumn;
    @FXML
    private TableColumn<ResultRow, String> aColumn;
    @FXML
    private TableColumn<ResultRow, String> bColumn;
    @FXML
    private TableColumn<ResultRow, String> aproxColumn;
    @FXML
    private TableColumn<ResultRow, String> faColumn;
    @FXML
    private TableColumn<ResultRow, String> fbColumn;
    @FXML
    private TableColumn<ResultRow, String> fcColumn;
    @FXML
    private TableColumn<ResultRow, String> errorColumn;
    @FXML
    private TextField iterationsField;
    @FXML
    private TextField errorField;

    // ObservableList para almacenar los resultados de la tabla
    private ObservableList<ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("first-menu");
    }


    @FXML
    private void calculateBisection() {
        tableData.clear();
        String function = functionField.getText();
        double a, b;
        int maxIterations = 0;
        double tolerance = 0;
        boolean useIterations = false;

        try {
            a = Double.parseDouble(lowerLimitField.getText());
            b = Double.parseDouble(upperLimitField.getText());
            String iterationsText = iterationsField.getText();
            String toleranceText = errorField.getText();

            if (!iterationsText.isEmpty()) {
                maxIterations = Integer.parseInt(iterationsText);
                useIterations = true;
            } else if (!toleranceText.isEmpty()) {
                tolerance = Double.parseDouble(toleranceText);
                useIterations = false;
            } else {
                showErrorAlert("Debe ingresar número de iteraciones o tolerancia máxima.");
                return;
            }

            BisectionMethod method = new BisectionMethod(a, b, maxIterations, tolerance, function);
            double prevC = Double.NaN;
            int totalIterations = useIterations ? maxIterations : (int)method.iterationsNeededError();
            double currA = a;
            double currB = b;
            for (int i = 1; i <= totalIterations; i++) {
                double fa = method.evaluateFunction(currA);
                double fb = method.evaluateFunction(currB);
                double c = (currA + currB) / 2.0;
                double fc = method.evaluateFunction(c);
                double error = (i == 1) ? 0.0 : Math.abs(c - prevC);
                prevC = c;

                String faSign = fa > 0 ? "+" : fa < 0 ? "-" : "0";
                String fbSign = fb > 0 ? "+" : fb < 0 ? "-" : "0";
                String fcSign = fc > 0 ? "+" : fc < 0 ? "-" : "0";

                tableData.add(new ResultRow(
                    i,
                    String.format("%.6f", currA),
                    String.format("%.6f", currB),
                    String.format("%.6f", c),
                    faSign,
                    fbSign,
                    fcSign,
                    String.format("%.6f", error)
                ));

                // Actualiza a y b según el método
                if (Math.signum(fa) == Math.signum(fc)) {
                    currA = c;
                } else {
                    currB = c;
                }
            }
            resultsTable.setItems(tableData);
        } catch (Exception ex) {
            showErrorAlert(ex.getMessage());
        }
    }

    // Método para mostrar ventana emergente de error
    private void showErrorAlert(String message) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    // Clase interna para la tabla
    public static class ResultRow {
        private final Integer iteration;
        private final String a, b, aproximacion, fa, fb, fc, error;
        public ResultRow(int iteration, String a, String b, String aproximacion, String fa, String fb, String fc, String error) {
            this.iteration = iteration;
            this.a = a;
            this.b = b;
            this.aproximacion = aproximacion;
            this.fa = fa;
            this.fb = fb;
            this.fc = fc;
            this.error = error;
        }
        public Integer getIteration() { return iteration; }
        public String getA() { return a; }
        public String getB() { return b; }
        public String getAproximacion() { return aproximacion; }
        public String getFa() { return fa; }
        public String getFb() { return fb; }
        public String getFc() { return fc; }
        public String getError() { return error; }
    }
}