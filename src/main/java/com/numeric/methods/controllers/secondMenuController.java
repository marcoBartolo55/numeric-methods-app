package com.numeric.methods.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import com.numeric.methods.App;

public class secondMenuController {

    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("main");
    }

}