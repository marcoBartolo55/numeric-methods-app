package com.numeric.methods.controllers;


import java.io.IOException;
import com.numeric.methods.App;
import com.numeric.methods.logic.FalsePositionMethod;

import javafx.fxml.FXML;

public class falsePositionController {
    
    @FXML
    public void initialize() {
        // Inicialización de la tabla y otros componentes si es necesario
    }

    @FXML
    private void switchToFalsePosition() throws Exception {
        App.setRoot("false-position");
    } 


}
