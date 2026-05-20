package com.numeric.methods.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import com.numeric.methods.App;

public class mainController {

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
}
