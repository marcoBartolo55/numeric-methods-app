package com.numeric.methods.controllers;

import java.io.IOException;
import com.numeric.methods.App;
import javafx.fxml.FXML;

public class dividedDifferencesController {
    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu-numerical-differentiation-integration");
    }
}
