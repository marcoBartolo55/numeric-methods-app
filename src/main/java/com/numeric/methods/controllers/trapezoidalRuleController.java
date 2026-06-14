package com.numeric.methods.controllers;

import java.io.IOException;

import com.numeric.methods.App;
import com.numeric.methods.logic.TrapezoidalRule;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class trapezoidalRuleController {
    
    // Componentes de la vista inyectados por FXML
    @FXML private ComboBox<String> methodComboBox;
    @FXML private TextField functionField;
    @FXML private TextField lowerLimitField;
    @FXML private TextField upperLimitField;
    @FXML private TextField numberTrapField;
    @FXML private TextField resultField;

    @FXML
    private void initialize() {
        methodComboBox.setItems(FXCollections.observableArrayList(
            "Simple",
            "Compuesta"
        ));

        methodComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
        
        clearFields();
        if ("Simple".equals(newValue)) {
            numberTrapField.setDisable(true);
            numberTrapField.setPromptText("No se requiere para este método");
        } else if ("Compuesta".equals(newValue)) {
            numberTrapField.setDisable(false);
            numberTrapField.setPromptText("Ej: 2");
        }
        });
    }

    @FXML
    private void calculateTrapezoidalRule() {

        String function = functionField.getText();
        String metodo = methodComboBox.getValue();

        if (function.isEmpty() || lowerLimitField.getText().isEmpty() || upperLimitField.getText().isEmpty()) {
            showErrorAlert("Por favor, complete los campos de función y límites de integración.");
            return;
        }

        if (metodo == null) {
            showErrorAlert("Por favor, selecciona un tipo de regla.");
            return;
        }

        try {
            double a = Double.parseDouble(lowerLimitField.getText());
            double b = Double.parseDouble(upperLimitField.getText());

            if ("Simple".equals(metodo)) {
                TrapezoidalRule trapezoidal = new TrapezoidalRule(function, a, b);
                double result = trapezoidal.calculateSimpleTrapezoidal();
                
                showInfoAlert(String.format("El resultado de la regla trapezoidal simple es: %.6f", result));
                resultField.setText(String.format("Resultado: %.6f", result));

            } else if ("Compuesta".equals(metodo)) {
                String trapStr = numberTrapField.getText().trim();
                
                if (trapStr.isEmpty()) {
                    showErrorAlert("Por favor, ingrese el número de trapecios (n).");
                    return;
                }

                int n = Integer.parseInt(trapStr);

                if (n <= 0) {
                    showErrorAlert("El número de trapecios (n) debe ser estrictamente mayor a cero.");
                    return;
                }

                TrapezoidalRule trapezoidal = new TrapezoidalRule(function, n, a, b);
                double result = trapezoidal.calculateCompositeTrapezoidal();
                
                showInfoAlert(String.format("El resultado de la regla trapezoidal compuesta es: %.6f", result));
                resultField.setText(String.format("Resultado: %.6f", result));
            }
            
        } catch (NumberFormatException e) {
            showErrorAlert("Los límites o el número de trapecios contienen caracteres numéricos inválidos.");
        } catch (Exception e) {
            showErrorAlert("Error al evaluar la función. Revise la sintaxis matemática.");
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

    private void clearFields() {
        functionField.clear();
        lowerLimitField.clear();
        upperLimitField.clear();
        numberTrapField.clear();
        resultField.clear();
    }
}
