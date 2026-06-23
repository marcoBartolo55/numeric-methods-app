package com.numeric.methods.logic;

import java.util.List;
import com.numeric.methods.logic.Derivative2Points.Point;

public class Derivative5Points {

    /**
     * Calcula la derivada usando la fórmula general para 5 puntos (Derivada interpolante de Lagrange).
     * Funciona para puntos igualmente y no igualmente espaciados.
     * * @param points Lista con los 5 puntos exactos.
     * @param targetX El valor de x donde se quiere evaluar la derivada.
     * @return El valor de la derivada aproximada en targetX.
     */
    public static double calculateDerivative(List<Point> points, double targetX) {
        if (points.size() != 5) {
            throw new IllegalArgumentException("Se requieren exactamente 5 puntos.");
        }

        double derivative = 0.0;
        int n = points.size();

        for (int i = 0; i < n; i++) {
            double xi = points.get(i).getX();
            double yi = points.get(i).getY();
            
            double sum = 0.0;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double product = 1.0;
                    for (int k = 0; k < n; k++) {
                        if (k != i && k != j) {
                            product *= (targetX - points.get(k).getX()) / (xi - points.get(k).getX());
                        }
                    }
                    sum += product / (xi - points.get(j).getX());
                }
            }
            derivative += yi * sum;
        }
        
        return derivative;
    }
}