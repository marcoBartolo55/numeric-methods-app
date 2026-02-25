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

    @FXML
    private TextField functionField;
    @FXML
    private TextField lowerLimitField;
    @FXML
    private TextField upperLimitField;

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

    private ObservableList<ResultRow> tableData = FXCollections.observableArrayList();

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("firstMenu");
    }

    @FXML
    private void calculateBisection() {
        try {
            int a = Integer.parseInt(lowerLimitField.getText());
            int b = Integer.parseInt(upperLimitField.getText());
            // int n = ... // puedes agregar un campo para n si lo necesitas
            bisecction_method bisec = new bisecction_method();
            int xm = bisec.bisection(a, b, 1);
            int error = bisec.error_bisection(a, b);
            // Ejemplo: agrega una fila a la tabla
            tableData.clear();
            tableData.add(new ResultRow(1, a, b, xm, 0, error));
            resultsTable.setItems(tableData);
        } catch (NumberFormatException e) {
            // Manejo de error: puedes mostrar un mensaje al usuario
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