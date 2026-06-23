package com.numeric.methods.logic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.ArrayList;
import java.util.List;

public class Derivative2Points {

    public static class Point {
        private final double x;
        private final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() { return x; }
        public double getY() { return y; }
    }

    public static class DerivativeRow {
        private final StringProperty variable;
        private final StringProperty[] puntos;

        public DerivativeRow(String variable, int numPuntos) {
            this.variable = new SimpleStringProperty(variable);
            this.puntos = new StringProperty[numPuntos];
            for (int i = 0; i < numPuntos; i++) {
                this.puntos[i] = new SimpleStringProperty("-");
            }
        }

        public String getVariable() { return variable.get(); }
        public StringProperty variableProperty() { return variable; }
        
        public void setPunto(int index, String valor) { this.puntos[index].set(valor); }
        public String getPunto(int index) { return this.puntos[index].get(); }
        public StringProperty puntoProperty(int index) { return puntos[index]; }
    }

    /**
     * Genera los puntos evaluando una función matemática usando h.
     */
    public static List<Point> generatePointsEquallySpaced(String function, double x0, double h, int numPoints) {
        Expression exp = new ExpressionBuilder(function).variable("x").build();
        List<Point> points = new ArrayList<>();
        
        for (int i = 0; i < numPoints; i++) {
            double x = x0 + (i * h);
            exp.setVariable("x", x);
            double y = exp.evaluate();
            points.add(new Point(x, y));
        }
        return points;
    }

    /**
     * Calcula la aproximación de la derivada (pendiente) entre dos puntos.
     */
    public static double calculateDerivative(Point p1, Point p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        return (dx != 0) ? (dy / dx) : 0.0;
    }
}