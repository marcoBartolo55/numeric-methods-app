package com.numeric.methods.logic;

import java.util.List;
import com.numeric.methods.logic.Derivative2Points.Point;

public class Derivative3Points {

    /**
     * Calcula la derivada usando la fórmula de 3 puntos (Derivada de Lagrange).
     * @param points Lista exacta con 3 puntos (Point).
     * @param targetX El valor de 'x' donde se quiere evaluar la derivada.
     * @return El valor de la derivada aproximada.
     */
    public static double calculateDerivative(List<Point> points, double targetX) {
        double x0 = points.get(0).getX(); double y0 = points.get(0).getY();
        double x1 = points.get(1).getX(); double y1 = points.get(1).getY();
        double x2 = points.get(2).getX(); double y2 = points.get(2).getY();

        // Derivadas de los polinomios base de Lagrange (L0', L1', L2')
        double dL0 = (2 * targetX - x1 - x2) / ((x0 - x1) * (x0 - x2));
        double dL1 = (2 * targetX - x0 - x2) / ((x1 - x0) * (x1 - x2));
        double dL2 = (2 * targetX - x0 - x1) / ((x2 - x0) * (x2 - x1));

        return (y0 * dL0) + (y1 * dL1) + (y2 * dL2);
    }
}