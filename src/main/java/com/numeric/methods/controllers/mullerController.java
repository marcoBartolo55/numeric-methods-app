package com.numeric.methods.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import com.numeric.methods.App;

public class mullerController {

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu-roots-aproximation");
    }

    @FXML
    private void switchToMuller() throws IOException {
        App.setRoot("muller");
    }

    @FXML
    private void switchToMullerIm() throws IOException {
        App.setRoot("mullerIm");
    }
}
