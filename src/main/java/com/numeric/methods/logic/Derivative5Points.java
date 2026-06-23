package com.numeric.methods.logic;

//! WARNING: Posible error de calculo en el método de puntos no igualmente espaciados. Revisar la fórmula utilizada para la aproximación de la derivada.

import java.util.ArrayList;
import java.util.List;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Derivative5Points {
    private double x0, h;
    private Expression expression;
    private List<Point> points;

    public Derivative5Points(String function, double x0, double h) {
        this.x0 = x0;
        this.h = h;
        try {
            this.expression = new ExpressionBuilder(function).variable("x").build();
        } catch (Exception e) {
            throw new IllegalArgumentException("Función inválida: " + function);
        }
    }

    public Derivative5Points(List<Point> points) {
        this.points = points;
    }

    public double evaluateFunction(double x) {
        if (expression == null) return 0.0;
        expression.setVariable("x", x);
        return expression.evaluate();
    }

    public double calculateBackward() {
        return (evaluateFunction(x0 - 2 * h) - 4 * evaluateFunction(x0 - h) + 3 * evaluateFunction(x0)) /  (2 * h);
    }

    public double calculateCentral() {
        return (evaluateFunction(x0 + h) - evaluateFunction(x0 - h)) / (2 * h);
    }

    public double calculateForward() {
        return (-3 * evaluateFunction(x0) + 4 * evaluateFunction(x0 + h) - evaluateFunction(x0 + 2 * h)) / (2 * h);
    }

    public List<ResultRow> generateTableData() {
        List<ResultRow> dataList = new ArrayList<>();
        
        if (points == null || points.size() < 5) {
            return dataList;
        }

        int n = points.size();

        for (int i = 0; i < n; i++) {
            double derivativeApprox = 0.0;

            if (i == 0) {
                derivativeApprox = calculateUnequalThreePoints(points.get(0), points.get(1), points.get(2), points.get(0).getX());
            } else if (i == n- 1) {
                derivativeApprox = calculateUnequalThreePoints(points.get(n - 3), points.get(n - 2), points.get(n - 1), points.get(n - 1).getX());
            } else {
                derivativeApprox = calculateUnequalThreePoints(points.get(i - 1), points.get(i), points.get(i + 1), points.get(i).getX());
            }

            dataList.add(new ResultRow(i, points.get(i).getX(), points.get(i).getY(), derivativeApprox));        }
        
        return dataList;
    }

    public static class Point {
        private final double x;
        private final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    public static class ResultRow {
        private final int iteration;
        private final double xi;
        private final double yi;
        private final double derivative;

        public ResultRow(int iteration, double xi, double yi, double derivative) {
            this.iteration = iteration;
            this.xi = xi;
            this.yi = yi;
            this.derivative = derivative;
        }

        public int getIteration() {
            return iteration;
        }

        public double getXi() {
            return xi;
        }

        public double getYi() {
            return yi;
        }

        public double getDerivative() {
            return derivative;
        }
    }

    private double calculateUnequalThreePoints(Point p0, Point p1, Point p2, double xEval) {
        double x0 = p0.getX();
        double x1 = p1.getX();
        double x2 = p2.getX();
        
        double y0 = p0.getY();
        double y1 = p1.getY();
        double y2 = p2.getY();

        
        double d0 = (x0 - x1) * (x0 - x2);
        double d1 = (x1 - x0) * (x1 - x2);
        double d2 = (x2 - x0) * (x2 - x1);

        
        if (d0 == 0 || d1 == 0 || d2 == 0) return 0.0;

        
        double n0 = 2.0 * xEval - x1 - x2;
        double n1 = 2.0 * xEval - x0 - x2;
        double n2 = 2.0 * xEval - x0 - x1;

        
        return (n0 / d0) * y0 + (n1 / d1) * y1 + (n2 / d2) * y2;
    }

}
