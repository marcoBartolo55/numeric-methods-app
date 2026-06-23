package com.numeric.methods.controllers;

import java.io.IOException;

import com.numeric.methods.App;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

public class leastSquaresController {

    @FXML private TextField pointsField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField functionField;
    @FXML private TextField xMinField;
    @FXML private TextField xMaxField;

    @FXML private TextField coefficientAField;
    @FXML private TextField coefficientBField;
    @FXML private TextField coefficientCField;

    @FXML private TableView<PuntoRegresion> dataTable;
    @FXML private TableColumn<PuntoRegresion, Integer> pointNumberCol;
    @FXML private TableColumn<PuntoRegresion, Double> xColumn;
    @FXML private TableColumn<PuntoRegresion, Double> yColumn;

    @FXML private ScatterChart<Number, Number> leastSquaresChart;

    private ObservableList<PuntoRegresion> listaPuntos =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        pointNumberCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        xColumn.setCellValueFactory(
                new PropertyValueFactory<>("x"));

        yColumn.setCellValueFactory(
                new PropertyValueFactory<>("y"));

        dataTable.setItems(listaPuntos);

        leastSquaresChart.setAnimated(false);

        System.out.println(
                "Controlador LeastSquares, Tabla y AutoRanging configurados con éxito.");
    }

    @FXML
    private void calculateRegression() {

        try {

            listaPuntos.clear();
            leastSquaresChart.getData().clear();

            String funcionStr = functionField.getText().trim();

            double xMin =
                    Double.parseDouble(xMinField.getText().trim());

            double xMax =
                    Double.parseDouble(xMaxField.getText().trim());

            int numPuntos =
                    Integer.parseInt(pointsField.getText().trim());

            if (numPuntos < 2) {
                return;
            }

            double paso = (xMax - xMin) / (numPuntos - 1);

            WeightedObservedPoints puntos =
                    new WeightedObservedPoints();

            Expression expresion =
                    new ExpressionBuilder(funcionStr)
                            .variables("x")
                            .build();

            XYChart.Series<Number, Number> seriePuntos =
                    new XYChart.Series<>();

            seriePuntos.setName("Puntos f(x)");

            System.out.println("\n=== PUNTOS EVALUADOS ===");

            for (int i = 0; i < numPuntos; i++) {

                double x = xMin + (i * paso);

                expresion.setVariable("x", x);

                double y = expresion.evaluate();

                puntos.add(x, y);

                listaPuntos.add(
                        new PuntoRegresion(i + 1, x, y));

                seriePuntos.getData().add(
                        new XYChart.Data<>(x, y));

                System.out.printf(
                        "Punto %d -> x: %.4f, y: %.4f%n",
                        i + 1, x, y);
            }

            leastSquaresChart.getData().add(seriePuntos);

            PolynomialCurveFitter ajustador =
                    PolynomialCurveFitter.create(2);

            double[] coeficientes =
                    ajustador.fit(puntos.toList());

            coefficientAField.setText(
                    String.format("%.6f", coeficientes[0]));

            coefficientBField.setText(
                    String.format("%.6f", coeficientes[1]));

            coefficientCField.setText(
                    String.format("%.6f", coeficientes[2]));

            System.out.println(
                    "¡Cálculo e inyección de datos listos!");

        } catch (Exception e) {

            System.out.println(
                    "Error al procesar los datos matemáticos: "
                            + e.getMessage());
        }
    }

    @FXML
    private void clearData() {

        functionField.clear();
        xMinField.clear();
        xMaxField.clear();
        pointsField.clear();

        coefficientAField.clear();
        coefficientBField.clear();
        coefficientCField.clear();

        listaPuntos.clear();
        leastSquaresChart.getData().clear();

        System.out.println("Campos limpios.");
    }

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu-numerical-differentiation-integration");
    }

    public static class PuntoRegresion {

        private final int id;
        private final double x;
        private final double y;

        public PuntoRegresion(int id, double x, double y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        public int getId() {
            return id;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }
}

