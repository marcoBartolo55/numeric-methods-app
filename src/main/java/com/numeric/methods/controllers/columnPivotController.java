package com.numeric.methods.controllers;

import com.numeric.methods.logic.ColumnPivot;
import com.numeric.methods.logic.ColumnPivot.VariableResult;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class columnPivotController implements Initializable {

    // Componentes inyectados desde el FXML
    @FXML private Spinner<Integer> matrixSizeSpinner;
    @FXML private ComboBox<String> methodComboBox;
    @FXML private Spinner<Integer> decimalsSpinner;
    @FXML private GridPane matrixGridPane;
    @FXML private TextArea logTextArea;
    @FXML private TableView<VariableResult> resultsTable;
    @FXML private TableColumn<VariableResult, String> variableColumn;
    @FXML private TableColumn<VariableResult, String> valueColumn;

    private TextField[][] matrixFields;
    private TextField[] vectorFields;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        variableColumn.setCellValueFactory(new PropertyValueFactory<>("variable"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        matrixSizeSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            generateMatrixGrid(newValue);
        });

        if (methodComboBox.getValue() == null) {
            methodComboBox.getSelectionModel().selectFirst();
        }

        generateMatrixGrid(matrixSizeSpinner.getValue());
    }

    private void generateMatrixGrid(int n) {
        matrixGridPane.getChildren().clear();
        matrixFields = new TextField[n][n];
        vectorFields = new TextField[n];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                TextField tf = new TextField("0");
                tf.setPrefWidth(60);
                tf.getStyleClass().add("text-field");
                matrixFields[row][col] = tf;
                matrixGridPane.add(tf, col, row);
            }

            Label separatorLabel = new Label("|");
            separatorLabel.getStyleClass().add("label");
            separatorLabel.setStyle("-fx-font-size: 14px; -fx-padding: 0 5 0 5;");
            matrixGridPane.add(separatorLabel, n, row);

            TextField tfB = new TextField("0");
            tfB.setPrefWidth(60);
            tfB.getStyleClass().add("text-field");
            tfB.setStyle("-fx-border-color: #90caf9;"); // Un color de borde distintivo para b
            vectorFields[row] = tfB;
            matrixGridPane.add(tfB, n + 1, row);
        }
    }

    @FXML
    private void handleCalculateAll() {
        int n = matrixSizeSpinner.getValue();
        double[][] A = new double[n][n];
        double[] b = new double[n];
        int decimals = decimalsSpinner.getValue();

        logTextArea.clear();
        resultsTable.getItems().clear();

        try {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    A[i][j] = Double.parseDouble(matrixFields[i][j].getText().trim());
                }
                b[i] = Double.parseDouble(vectorFields[i].getText().trim());
            }
        } catch (NumberFormatException e) {
            logTextArea.setText("Error de Entrada: Por favor, asegúrate de llenar todas las celdas con números válidos.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        List<VariableResult> solvedVariables = ColumnPivot.solveWithScaledPartialPivot(A, b, decimals, sb);

        logTextArea.setText(sb.toString());
        if (solvedVariables != null) {
            resultsTable.getItems().addAll(solvedVariables);
        }
    }

    @FXML
    private void handleNextStep() {

        handleCalculateAll();
    }

    @FXML
    private void switchToMenu() {
        try {
            // Reemplaza "App" y el método por tu cargador de escenas global
             com.numeric.methods.App.setRoot("menu-roots-approximation");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}