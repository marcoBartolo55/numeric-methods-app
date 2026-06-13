package com.numeric.methods.controllers;

import java.io.IOException;
import com.numeric.methods.App;
import com.numeric.methods.logic.SimpsonRule13;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class simpsonRule13Controller {

    // Componentes de la vista inyectados por FXML
    @FXML private TextField functionField;
    @FXML private TextField initialPointField; // Límite inferior (a)
    @FXML private TextField finalPointField;   // Límite superior (b)
    @FXML private TextField resultField;

    @FXML
    private void calculateSimpson13() {
        String function = functionField.getText().trim();
        String initialP = initialPointField.getText().trim();
        String finalP = finalPointField.getText().trim();

        // Validar campos vacíos
        if (function.isEmpty() || initialP.isEmpty() || finalP.isEmpty()) {
            showErrorAlert("Por favor, complete todos los campos.");
            return;
        }

        try {
            double a = Double.parseDouble(finalP); // Límite inferior
            double b = Double.parseDouble(initialP);   // Límite superior

            if (a == b) {
                showErrorAlert("El límite inferior y el límite superior no pueden ser iguales.");
                return;
            }

            if (a > b) {
                showErrorAlert("El límite inferior debe ser menor que el límite superior.");
                return;
            }

            SimpsonRule13 simpson13 = new SimpsonRule13(function, a, b);
            
            double result = simpson13.calculateIntegral(); 
            
            resultField.setText(String.format("%.6f", result));
            showInfoAlert("Cálculo completado. El resultado se muestra en el campo de resultado.");

        } catch (NumberFormatException e) {
            showErrorAlert("Por favor, ingrese valores numéricos válidos en los campos de límites.");
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