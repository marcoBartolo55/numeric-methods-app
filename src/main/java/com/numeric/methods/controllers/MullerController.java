package com.numeric.methods.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.numeric.methods.App;

// Librerías externas
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.math3.complex.Complex;

public class MullerController {

    @FXML private TextField functionField;
    @FXML private TextField p0Field;
    @FXML private TextField p1Field;
    @FXML private TextField p2Field;
    @FXML private TextField iterationsField;
    @FXML private TextField errorField;

    @FXML private TableView<FilaMuller> dataTable;
    @FXML private TableColumn<FilaMuller, Integer> iterCol;
    @FXML private TableColumn<FilaMuller, String> aCol;
    @FXML private TableColumn<FilaMuller, String> bCol;
    @FXML private TableColumn<FilaMuller, String> cCol;
    @FXML private TableColumn<FilaMuller, String> faCol;
    @FXML private TableColumn<FilaMuller, String> fbCol;
    @FXML private TableColumn<FilaMuller, String> fcCol;
    @FXML private TableColumn<FilaMuller, String> approxCol;
    @FXML private TableColumn<FilaMuller, String> errorCol;

    private ObservableList<FilaMuller> listaMuller = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        iterCol.setCellValueFactory(new PropertyValueFactory<>("iter"));
        aCol.setCellValueFactory(new PropertyValueFactory<>("a"));
        bCol.setCellValueFactory(new PropertyValueFactory<>("b"));
        cCol.setCellValueFactory(new PropertyValueFactory<>("c"));
        faCol.setCellValueFactory(new PropertyValueFactory<>("fa"));
        fbCol.setCellValueFactory(new PropertyValueFactory<>("fb"));
        fcCol.setCellValueFactory(new PropertyValueFactory<>("fc"));
        approxCol.setCellValueFactory(new PropertyValueFactory<>("approx"));
        errorCol.setCellValueFactory(new PropertyValueFactory<>("error"));

        dataTable.setItems(listaMuller);
    }

    @FXML
    private void calculateMuller() {
        try {
            listaMuller.clear();
            
            String funcStr = functionField.getText().trim();
            double x0 = Double.parseDouble(p0Field.getText().trim());
            double x1 = Double.parseDouble(p1Field.getText().trim());
            double x2 = Double.parseDouble(p2Field.getText().trim());
            int maxIter = Integer.parseInt(iterationsField.getText().trim());
            double tol = Double.parseDouble(errorField.getText().trim());

            Expression exp = new ExpressionBuilder(funcStr).variables("x").build();

            for (int i = 1; i <= maxIter; i++) {
                exp.setVariable("x", x0); double f0 = exp.evaluate();
                exp.setVariable("x", x1); double f1 = exp.evaluate();
                exp.setVariable("x", x2); double f2 = exp.evaluate();

                double h0 = x1 - x0;
                double h1 = x2 - x1;
                
                // Evitar división por cero si los puntos son iguales
                if (h0 == 0 || h1 == 0) break;

                double d0 = (f1 - f0) / h0;
                double d1 = (f2 - f1) / h1;

                double a = (d1 - d0) / (h1 + h0);
                double b = a * h1 + d1;
                double c_coef = f2;

                double discriminante = (b * b) - (4 * a * c_coef);

                if (discriminante < 0) {
                    // CÁLCULO DE RAÍZ IMAGINARIA
                    Complex cb = new Complex(b, 0);
                    Complex cc = new Complex(c_coef, 0);
                    Complex cDisc = new Complex(discriminante, 0).sqrt();

                    Complex den1 = cb.add(cDisc);
                    Complex den2 = cb.subtract(cDisc);
                    Complex den = (den1.abs() > den2.abs()) ? den1 : den2;

                    Complex dx = new Complex(-2, 0).multiply(cc).divide(den);
                    Complex x3 = new Complex(x2, 0).add(dx);

                    listaMuller.add(new FilaMuller(i, 
                        formatDouble(x0), formatDouble(x1), formatDouble(x2),
                        formatDouble(f0), formatDouble(f1), formatDouble(f2),
                        formatComplex(x3), "Imaginario"));
                    
                    System.out.println("Raíz compleja encontrada. Se detiene porque exp4j no evalúa f(imaginario).");
                    break; 
                } else {
                    // CÁLCULO DE RAÍZ REAL
                    double den1 = b + Math.sqrt(discriminante);
                    double den2 = b - Math.sqrt(discriminante);
                    double den = (Math.abs(den1) > Math.abs(den2)) ? den1 : den2;

                    double dx = -2 * c_coef / den;
                    double x3 = x2 + dx;
                    double errorCalc = Math.abs((x3 - x2) / x3) * 100; // Error relativo porcentual

                    listaMuller.add(new FilaMuller(i, 
                        formatDouble(x0), formatDouble(x1), formatDouble(x2),
                        formatDouble(f0), formatDouble(f1), formatDouble(f2),
                        formatDouble(x3), formatDouble(errorCalc) + "%"));

                    if (Math.abs(errorCalc) < tol || Math.abs(f2) < 1e-15) break;

                    x0 = x1;
                    x1 = x2;
                    x2 = x3;
                }
            }
        } catch (Exception e) {
            System.out.println("Error en Muller: " + e.getMessage());
        }
    }

    @FXML
    private void clearData() {
        functionField.clear();
        p0Field.clear();
        p1Field.clear();
        p2Field.clear();
        iterationsField.clear();
        errorField.clear();
        listaMuller.clear();
    }

    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu-roots-aproximation");
    }

    // Utilidades para formatear texto limpio
    private String formatDouble(double val) {
        return String.format("%.5f", val);
    }

    private String formatComplex(Complex c) {
        if (c.getImaginary() >= 0) {
            return String.format("%.4f + %.4fi", c.getReal(), c.getImaginary());
        } else {
            return String.format("%.4f - %.4fi", c.getReal(), Math.abs(c.getImaginary()));
        }
    }

    // CLASE MODELO INTERNA
    public static class FilaMuller {
        private final int iter;
        private final String a, b, c, fa, fb, fc, approx, error;

        public FilaMuller(int iter, String a, String b, String c, String fa, String fb, String fc, String approx, String error) {
            this.iter = iter; this.a = a; this.b = b; this.c = c;
            this.fa = fa; this.fb = fb; this.fc = fc;
            this.approx = approx; this.error = error;
        }

        public int getIter() { return iter; }
        public String getA() { return a; }
        public String getB() { return b; }
        public String getC() { return c; }
        public String getFa() { return fa; }
        public String getFb() { return fb; }
        public String getFc() { return fc; }
        public String getApprox() { return approx; }
        public String getError() { return error; }
    }
}
