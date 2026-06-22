package com.numeric.methods.controllers;

import com.numeric.methods.App;
import com.numeric.methods.logic.LagrangeInterpolation;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class lagrangeController {
    @FXML private TextField xInput, yInput, targetXField, resultField;

    @FXML
    public void calculateLagrange() {
        try {
            double[] x = parseInput(xInput.getText());
            double[] y = parseInput(yInput.getText());
            double target = Double.parseDouble(targetXField.getText());

            LagrangeInterpolation solver = new LagrangeInterpolation(x, y);
            double res = solver.calculate(target);
            resultField.setText(String.format("%.6f", res));
        } catch (Exception e) {
            resultField.setText("Error");
        }
    }

    private double[] parseInput(String input) {
        String[] parts = input.split(",");
        double[] arr = new double[parts.length];
        for(int i=0; i<parts.length; i++) arr[i] = Double.parseDouble(parts[i].trim());
        return arr;
    }

    @FXML
    private void switchToMenu() throws Exception { App.setRoot("menu-numerical-differentiation-integration"); }
}