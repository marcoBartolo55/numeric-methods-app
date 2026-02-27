package com.numeric.methods.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import com.numeric.methods.App;
import com.numeric.methods.logic.bisecction_method;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class bisectionController {

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
    private TableColumn<ResultRow, Integer> aColumn;
    @FXML
    private TableColumn<ResultRow, Integer> bColumn;
    @FXML
    private TableColumn<ResultRow, Integer> xmColumn;
    @FXML
    private TableColumn<ResultRow, Integer> fxmColumn;
    @FXML
    private TableColumn<ResultRow, Integer> errorColumn;
    @FXML
    private TextField iterationsField;

    // ObservableList para almacenar los resultados de la tabla
    private ObservableList<ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("firstMenu");
    }


    @FXML
    private void calculateBisection() {
        try {
            // Recibido del valor de las variables de entrada
            double a = Double.parseDouble(lowerLimitField.getText());
            double b = Double.parseDouble(upperLimitField.getText());
            int n = Integer.parseInt(iterationsField.getText());
            String function = functionField.getText();

            bisecction_method bisection = new bisecction_method();

            // Validación
            if (!bisection.validation(n)) {
                throw new IllegalArgumentException("Algún mensaje de error");
            }

            // Calculo
            double xm = bisection.bisection(a, b, n);
            double error = bisection.error_bisection(a, b);

            // Manejo de tabla: Ingresar filas
            tableData.clear();
            // tableData.add(new ResultRow(1, a, b, xm, 0, error));
            resultsTable.setItems(tableData);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }

    // Clase interna para la tabla
    public static class ResultRow {
        private final Integer iteration, a, b, xm, fxm, error;
        public ResultRow(int iteration, int a, int b, int xm, int fxm, int error) {
            this.iteration = iteration;
            this.a = a;
            this.b = b;
            this.xm = xm;
            this.fxm = fxm;
            this.error = error;
        }
        public Integer getIteration() { return iteration; }
        public Integer getA() { return a; }
        public Integer getB() { return b; }
        public Integer getXm() { return xm; }
        public Integer getFxm() { return fxm; }
        public Integer getError() { return error; }
    }
}