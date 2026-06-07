package com.numeric.methods.controllers;

import java.io.IOException;

import com.numeric.methods.App;

import javafx.fxml.FXML;

public class derivation3PointsController {
    
    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu-roots-aproximation");
    }
}
