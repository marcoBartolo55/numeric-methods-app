package com.numeric.methods.controllers;

import com.numeric.methods.App;
import com.numeric.methods.logic.FalsePositionMethod;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class falsePositionController {

    @FXML
    private TextField functionField;
    @FXML
    private TextField lowerLimitField;
    @FXML
    private TextField upperLimitField;
    @FXML
    private TextField iterationsField;
    @FXML
    private TextField errorField;

    @FXML
    private TableView<FalsePositionResult> resultsTable;
    @FXML
    private TableColumn<FalsePositionResult, Integer> iterationColumn;
    @FXML
    private TableColumn<FalsePositionResult, Double> aColumn;
    @FXML
    private TableColumn<FalsePositionResult, Double> bColumn;
    @FXML
    private TableColumn<FalsePositionResult, Double> cColumn;
    @FXML
    private TableColumn<FalsePositionResult, Double> aproxColumn;
    @FXML
    private TableColumn<FalsePositionResult, Double> errorColumn;
    
    @FXML
    private void switchToMenu() throws Exception {
        App.setRoot("menu-roots-aproximation");
    } 


    @FXML
    public void initialize() {
        // Inicialización de la tabla y columnas
        if (iterationColumn != null) iterationColumn.setCellValueFactory(new PropertyValueFactory<>("iteration"));
        if (aColumn != null) aColumn.setCellValueFactory(new PropertyValueFactory<>("a"));
        if (bColumn != null) bColumn.setCellValueFactory(new PropertyValueFactory<>("b"));
        if (cColumn != null) cColumn.setCellValueFactory(new PropertyValueFactory<>("c"));
        if (aproxColumn != null) aproxColumn.setCellValueFactory(new PropertyValueFactory<>("aprox"));
        if (errorColumn != null) errorColumn.setCellValueFactory(new PropertyValueFactory<>("error"));
    }

    @FXML
    private void calculateFalsePosition(javafx.event.ActionEvent event) {
        try {
            String function = functionField.getText();
            double a = Double.parseDouble(lowerLimitField.getText());
            double b = Double.parseDouble(upperLimitField.getText());
            int iterations = Integer.parseInt(iterationsField.getText());
            double tol = Double.parseDouble(errorField.getText());

            ObservableList<FalsePositionResult> data = FXCollections.observableArrayList();

            double xr = 0, prevXr = 0, err = 1;
            FalsePositionMethod method = new FalsePositionMethod(a, b, xr, 0, tol, function);

            for (int i = 1; i <= iterations && err > tol; i++) {
                prevXr = xr;
                xr = method.reasignValueBySign();
                err = (i == 1) ? 1 : Math.abs((xr - prevXr) / xr);
                data.add(new FalsePositionResult(i, method.getX0(), method.getX1(), xr, xr, err));
                if (Math.abs(method.evaluateFunction(xr)) < tol) break;
            }

            resultsTable.setItems(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static class FalsePositionResult {
        private final Integer iteration;
        private final Double a;
        private final Double b;
        private final Double c;
        private final Double aprox;
        private final Double error;

        public FalsePositionResult(Integer iteration, Double a, Double b, Double c, Double aprox, Double error) {
            this.iteration = iteration;
            this.a = a;
            this.b = b;
            this.c = c;
            this.aprox = aprox;
            this.error = error;
        }
        public Integer getIteration() { return iteration; }
        public Double getA() { return a; }
        public Double getB() { return b; }
        public Double getC() { return c; }
        public Double getAprox() { return aprox; }
        public Double getError() { return error; }
    }



}
