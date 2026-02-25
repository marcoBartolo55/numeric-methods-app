package com.numeric.methods.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import com.numeric.methods.App;

public class mainController {

    @FXML
    private void switchToFirstMenu() throws IOException {
        App.setRoot("firstMenu");
    }
}
