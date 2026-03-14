package com.numeric.methods.controllers;

import com.numeric.methods.logic.convertion;

import com.numeric.methods.App;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class convertionController {
    @FXML
    private TextField decimalField;

    @FXML
    private TextField signField;

    @FXML
    private ComboBox<String> bitsComboBox;

    @FXML
    private Button calculateButton;

    @FXML
    private TextArea resultArea;

    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    private void switchToMenu() throws Exception {
        App.setRoot("first-menu");
    }


    @FXML
    private void calculateBinaryConversion() {
        try {
            String decimalText = decimalField.getText();
            String signText = signField.getText().trim();
            boolean isNegative = signText.equalsIgnoreCase("S");
            int bitsNumber = Integer.parseInt(bitsComboBox.getValue());

            convertion convertion = new convertion(Integer.parseInt(decimalText), isNegative, bitsNumber);
            resultArea.setText(convertion.decimalToBinary());
        } catch (NumberFormatException e) {
            showAlert("Alerta", "Entrada inválida", "Por favor, ingrese números válidos.", Alert.AlertType.ERROR);
        }
    }

}
