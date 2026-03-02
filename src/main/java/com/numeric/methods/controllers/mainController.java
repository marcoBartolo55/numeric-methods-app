package com.numeric.methods.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import com.numeric.methods.App;

public class mainController {

    @FXML
    private void switchToFirstMenu() throws IOException {
        App.setRoot("first-menu");
    }

    @FXML
    private void switchToSecondMenu() throws IOException {
        App.setRoot("second-menu");
    }

    @FXML
    private void switchToThirdMenu() throws IOException {
        App.setRoot("third-menu");
    }

    @FXML
    private void exitApplication() throws IOException {
        System.exit(0);
    }
}
