package com.numeric.methods.controllers;

import java.io.IOException;
import com.numeric.methods.App;
import com.numeric.methods.logic.AdaptiveSimpson;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class adaptiveSimpsonController {

    // Componentes de la vista
    @FXML private TextField functionField;
    @FXML private TextField lowerLimitField;
    @FXML private TextField upperLimitField;
    @FXML private TextField toleranceField;
    
    // Label para mostrar el resultado final, ya que no es una tabla iterativa
    @FXML private TextField resultField;

    @FXML
    public void initialize() {
        // Inicialización necesaria si tus componentes requieren estados previos
    }

    @FXML
    private void calculateAdaptiveSimpson() {

        if (functionField.getText().trim().isEmpty() || 
            lowerLimitField.getText().trim().isEmpty() || 
            upperLimitField.getText().trim().isEmpty() || 
            toleranceField.getText().trim().isEmpty()) {
            showErrorAlert("Por favor, completa todos los campos.");
            return;
        }

        try {
            String function = functionField.getText().trim();
            double a = Double.parseDouble(lowerLimitField.getText().trim());
            double b = Double.parseDouble(upperLimitField.getText().trim());
            double tol = Double.parseDouble(toleranceField.getText().trim());

            AdaptiveSimpson solver = new AdaptiveSimpson(function);
            double result = solver.integrate(a, b, tol);

            showInfoAlert(String.format("La integral aproximada es: %.8f", result));
            resultField.setText(String.format("%.8f", result));
            
        } catch (NumberFormatException e) {
            showErrorAlert("Por favor, ingresa valores numéricos válidos para los límites y la tolerancia.");
        } catch (Exception e) {
            showErrorAlert("Ocurrió un error al calcular la integral: " + e.getMessage());
        }
    }
    
    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu-ordinary-differential-equations");
    }

    private void showErrorAlert(String message) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Error de entrada");
            alert.setHeaderText("Entrada no válida");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private void showInfoAlert(String message) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Resultado");
            alert.setHeaderText("Cálculo completado");
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}