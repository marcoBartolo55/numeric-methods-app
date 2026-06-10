package com.numeric.methods.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import java.io.IOException;
import com.numeric.methods.App;

public class leastSquaresController { // Primera letra en Mayúscula

    @FXML private TextField pointsField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField coefficientAField;
    @FXML private TextField coefficientBField;
    @FXML private TextField coefficientCField;
    
    @FXML private TableView<?> resultsTable;
    @FXML private TableColumn<?, ?> pointNumber;
    @FXML private TableColumn<?, ?> x_results;
    @FXML private TableColumn<?, ?> y_results;

    @FXML
    public void initialize() {
        // Inicialización base
    }

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu-numerical-differentiation-integration");
    }
}