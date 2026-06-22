package com.numeric.methods.controllers;

import com.numeric.methods.App;
import com.numeric.methods.logic.LagrangeInterpolation;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class lagrangeController {
    @FXML private TextField xInput, yInput, targetXField, resultField;
    private double[] x;
    private double[] y;

    @FXML
    public void calculateLagrange() {
        try {
            
            this.x = parseInput(xInput.getText());
            this.y = parseInput(yInput.getText());
            
            LagrangeInterpolation solver = new LagrangeInterpolation(x, y);
            String targetText = targetXField.getText().trim();

            
            if (targetText.isEmpty() || targetText.equals("Ej: 1.5")) {
                resultField.setText(solver.getPolynomialExpression());
            } else {
                double target = Double.parseDouble(targetText);
                double res = solver.calculate(target);
                resultField.setText(String.format("%.6f", res));
            }
        } catch (Exception e) {
            resultField.setText("Error: Verifica tus datos");
        }
    }

    @FXML
    public void showExpression() {
        try {
            if (x == null || y == null) {
                
                this.x = parseInput(xInput.getText());
                this.y = parseInput(yInput.getText());
            }
            LagrangeInterpolation solver = new LagrangeInterpolation(x, y);
            resultField.setText(solver.getPolynomialExpression());
        } catch (Exception e) {
            resultField.setText("Error al generar expresión");
        }
    }

    private double[] parseInput(String input) {
        String[] parts = input.split(",");
        double[] arr = new double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            try {
                arr[i] = Double.parseDouble(parts[i].trim());
            } catch (NumberFormatException e) {
                System.out.println("Error en el índice " + i + ": '" + parts[i] + "' no es un número.");
                throw e; // Esto detendrá el programa para que veas el error
            }
        }
        return arr;
    }

    @FXML
    private void switchToMenu() throws Exception { 
        App.setRoot("menu-numerical-differentiation-integration"); 
    }
}