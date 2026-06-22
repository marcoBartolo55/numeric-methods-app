package com.numeric.methods.controllers;

import com.numeric.methods.App;
import javafx.fxml.FXML;

public class rungeKuttaFelhbergController {
    @FXML
    private void switchToMenu() throws Exception {
        App.setRoot("menu-ordinary-differential-equations");
    }
}
