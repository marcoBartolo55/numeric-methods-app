package com.numeric.methods.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import com.numeric.methods.App;

public class firstMenuController {
    // Menú principal
    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("main");
    }

    // Métodos numéricos
    @FXML
    private void switchToConvertion() throws IOException {
        App.setRoot("convertion");
    }

    @FXML
    private void switchToBisection() throws IOException {
        App.setRoot("bisection");
    }

    @FXML
    private void switchToFixPoint() throws IOException {
        App.setRoot("fixed-point-iteration");
    }

    @FXML
    private void switchToNewtonRaphson() throws IOException {
        App.setRoot("newton-raphson");
    }
    
    @FXML
    private void switchToFalsePosition() throws IOException {
        App.setRoot("false-position");
    }

    @FXML
    private void switchToSecant() throws IOException {
        App.setRoot("secant");
    }

    @FXML
    private void switchToMuller() throws IOException {
        App.setRoot("muller");
    }

    @FXML
    private void switchToDeflation() throws IOException {
        App.setRoot(("deflation"));
    }

}
