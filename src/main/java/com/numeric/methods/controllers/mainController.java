package com.numeric.methods.controllers;

import javafx.scene.control.Tooltip;
import javafx.scene.control.Button;
import javafx.util.Duration;
import java.io.IOException;
import javafx.fxml.FXML;
import com.numeric.methods.App;

public class mainController {

    // Componentes de la vista
    @FXML private Button rootsAproximationButton;
    @FXML private Button numericalDifferentiationIntegrationButton;
    @FXML private Button ordinaryDifferentialEquationsButton;
    @FXML private Button exitButton;

    @FXML
    private void switchToRootsAproximationMenu() throws IOException {
        App.setRoot("menu-roots-aproximation");
    }

    @FXML
    private void switchToNumericalDifferentiationIntegration() throws IOException {
        App.setRoot("menu-numerical-differentiation-integration");
    }

    @FXML
    private void switchToOrdinaryDifferentialEquations() throws IOException {
        App.setRoot("menu-ordinary-differential-equations");
    }

    @FXML
    private void exitApplication() throws IOException {
        System.exit(0);
    }


    @FXML
    public void initialize() {
        String[] tooltipTexts = {
            "Solución de ecuaciones y sistemas lineales",
            "Aproximación e interpolación de funciones",
            "Cálculo numérico (diferenciación, integración y EDOs)",
            "Salir de la aplicación"
        };

        applyTooltip(rootsAproximationButton, tooltipTexts[0]);
        applyTooltip(numericalDifferentiationIntegrationButton, tooltipTexts[1]);
        applyTooltip(ordinaryDifferentialEquationsButton, tooltipTexts[2]);
        applyTooltip(exitButton, tooltipTexts[3]);
    }

    private void applyTooltip(Button button, String text) {
        Tooltip tooltip = new Tooltip(text);
        tooltip.setShowDelay(Duration.millis(300)); 
        button.setTooltip(tooltip);
    }
}
