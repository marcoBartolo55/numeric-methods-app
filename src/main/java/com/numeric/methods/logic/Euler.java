package com.numeric.methods.logic;

import java.util.ArrayList;
import java.util.List;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Euler {
    private final double x0Initial;
    private final double y0Initial;
    private final double xf;
    private final double h;
    private Expression expression;

    public Euler(String function, double x0, double y0, double xf, double h) {
        this.x0Initial = x0;
        this.y0Initial = y0;
        this.xf = xf;
        this.h = h;
        try {
            this.expression = new ExpressionBuilder(function).variables("x", "y").build();
        } catch (Exception e) {
            throw new IllegalArgumentException("Función inválida: " + function);
        }
    }

    public double evaluateFunction(double x, double y) {
        if (expression == null) return 0.0;
        expression.setVariable("x", x);
        expression.setVariable("y", y);
        return expression.evaluate();
    }

    public List<ResultRow> generateTableData() {
        List<ResultRow> dataList = new ArrayList<>();
        
        double xAct = x0Initial;
        double yAct = y0Initial;
        int iteration = 0;

        while (xAct <= xf + (h / 2.0)) {
            double slope = evaluateFunction(xAct, yAct);
            
            dataList.add(new ResultRow(iteration, xAct, yAct, slope));
            
            yAct = yAct + (h * slope);
            xAct = xAct + h;
            
            iteration++;
        }

        return dataList;
    }

    public static class ResultRow {
        private final int iteration;
        private final double x;
        private final double y;
        private final double slope;

        public ResultRow(int iteration, double x, double y, double slope) {
            this.iteration = iteration;
            this.x = x;
            this.y = y;
            this.slope = slope;
        }

        public int getIteration() { return iteration; }
        public String getX() { return String.valueOf(x); }
        public String getY() { return String.valueOf(y); }
        public String getSlope() { return String.valueOf(slope); }
    }
}