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

    // Matriz bidimensional de campos de texto para capturar los datos de la UI
    private TextField[][] matrixFields;
    private TextField[] vectorFields;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Configurar las columnas de la tabla de resultados finales
        variableColumn.setCellValueFactory(new PropertyValueFactory<>("variable"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        // 2. Escuchar cambios en el Spinner de dimensión para redibujar la matriz en tiempo real
        matrixSizeSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            generateMatrixGrid(newValue);
        });

        // 3. Selección por defecto del ComboBox si estuviera vacío
        if (methodComboBox.getValue() == null) {
            methodComboBox.getSelectionModel().selectFirst();
        }

        // 4. Generar la cuadrícula inicial (Tamaño por defecto = 3)
        generateMatrixGrid(matrixSizeSpinner.getValue());
    }

    /**
     * Dibuja dinámicamente los TextFields dentro del GridPane basándose en n
     */
    private void generateMatrixGrid(int n) {
        matrixGridPane.getChildren().clear(); // Limpiar dibujos anteriores
        matrixFields = new TextField[n][n];
        vectorFields = new TextField[n];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                TextField tf = new TextField("0");
                tf.setPrefWidth(60);
                tf.getStyleClass().add("text-field"); // Hereda tu CSS perfectamente
                matrixFields[row][col] = tf;
                matrixGridPane.add(tf, col, row);
            }

            // Añadir un separador visual o espacio antes del vector b
            Label separatorLabel = new Label("|");
            separatorLabel.getStyleClass().add("label");
            separatorLabel.setStyle("-fx-font-size: 14px; -fx-padding: 0 5 0 5;");
            matrixGridPane.add(separatorLabel, n, row);

            // Campo para el término independiente b_i
            TextField tfB = new TextField("0");
            tfB.setPrefWidth(60);
            tfB.getStyleClass().add("text-field");
            tfB.setStyle("-fx-border-color: #90caf9;"); // Un color de borde distintivo para b
            vectorFields[row] = tfB;
            matrixGridPane.add(tfB, n + 1, row);
        }
    }

    /**
     * Acción ejecutada por el botón "Resolver Todo"
     */
    @FXML
    private void handleCalculateAll() {
        int n = matrixSizeSpinner.getValue();
        double[][] A = new double[n][n];
        double[] b = new double[n];
        int decimals = decimalsSpinner.getValue();

        // Limpiar pantallas
        logTextArea.clear();
        resultsTable.getItems().clear();

        // 1. Leer y validar los datos numéricos ingresados en la cuadrícula
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

        // 2. Invocar la lógica de resolución
        StringBuilder sb = new StringBuilder();
        List<VariableResult> solvedVariables = ColumnPivot.solveWithScaledPartialPivot(A, b, decimals, sb);

        // 3. Pintar los resultados en la interfaz
        logTextArea.setText(sb.toString());
        if (solvedVariables != null) {
            resultsTable.getItems().addAll(solvedVariables);
        }
    }

    /**
     * Acción ejecutada por el botón "Paso a Paso"
     */
    @FXML
    private void handleNextStep() {
        // Aquí puedes adaptar un flujo iterativo si necesitas pausar en cada k
        // Por ahora, redirige al cálculo completo para pruebas iniciales
        handleCalculateAll();
    }

    /**
     * Acción del botón "Volver al Menú"
     */
    @FXML
    private void switchToMenu() {
        try {
            // Reemplaza "App" y el método por tu cargador de escenas global
             com.numeric.methods.App.setRoot("menu-principal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}