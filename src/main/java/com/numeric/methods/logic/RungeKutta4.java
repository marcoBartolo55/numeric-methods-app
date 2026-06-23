package com.numeric.methods.logic;

import java.util.ArrayList;
import java.util.List;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class RungeKutta4 {
    private final double x0Initial;
    private final double y0Initial;
    private final double xf;
    private final double h;
    private Expression expression;

    public RungeKutta4(String function, double x0, double y0, double xf, double h) {
        this.x0Initial = x0;
        this.y0Initial = y0;
        this.xf = xf;
        this.h = h;
        try {
            this.expression = new ExpressionBuilder(function).variables("x", "y").build();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La función proporcionada no es válida: " + e.getMessage());
        }
    }

    public double evaluateFunction(double x, double y) {
        if (expression == null) return 0.0;
        expression.setVariable("x", x);
        expression.setVariable("y", y);
        return expression.evaluate();
    }

    public double calculateK1(double x, double y) {
        return evaluateFunction(x, y);
    }

    public double calculateK2(double x, double y, double k1) {
        return evaluateFunction(x + (h / 2.0), y + (h / 2.0) * k1);
    }

    public double calculateK3(double x, double y, double k2) {
        return evaluateFunction(x + (h / 2.0), y + (h / 2.0) * k2);
    }

    public double calculateK4(double x, double y, double k3) {
        return evaluateFunction(x + h, y + h * k3);
    }

    public List<ResultRow> generateTableData() {
        List<ResultRow> dataList = new ArrayList<>();
        
        double xAct = x0Initial;
        double yAct = y0Initial;
        int iteration = 0;

        while (xAct <= xf + (h / 2.0)) {
            
            double k1 = calculateK1(xAct, yAct);
            double k2 = calculateK2(xAct, yAct, k1);
            double k3 = calculateK3(xAct, yAct, k2);
            double k4 = calculateK4(xAct, yAct, k3);
            
            double meanSlope = (k1 + (2.0 * k2) + (2.0 * k3) + k4) / 6.0;
            
            dataList.add(new ResultRow(iteration, xAct, yAct, k1, k2, k3, k4));
            
            yAct = yAct + (h * meanSlope);
            xAct = xAct + h;
            
            iteration++;
        }

        return dataList;
    }

    public static class ResultRow {
        private final int iteration;
        private final double x;
        private final double y;
        private final double k1;
        private final double k2;
        private final double k3;
        private final double k4;

        public ResultRow(int iteration, double x, double y, double k1, double k2, double k3, double k4) {
            this.iteration = iteration;
            this.x = x;
            this.y = y;
            this.k1 = k1;
            this.k2 = k2;
            this.k3 = k3;
            this.k4 = k4;
        }

        public int getIteration() { return iteration; }
        public String getX() { return String.valueOf(x); }
        public String getY() { return String.valueOf(y); }
        public String getK1() { return String.valueOf(k1); }
        public String getK2() { return String.valueOf(k2); }
        public String getK3() { return String.valueOf(k3); }
        public String getK4() { return String.valueOf(k4); }
    }
}