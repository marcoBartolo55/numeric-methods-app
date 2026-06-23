package com.numeric.methods.logic;

import java.util.ArrayList;
import java.util.List;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MultipleSimpson {
    private double b, a;
    private int n;
    private final Expression expression;

    public MultipleSimpson(String function, double a, double b, int n) {
        this.a = a;
        this.b = b;
        this.n = n;
        try {
            this.expression = new ExpressionBuilder(function).variable("x").build();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La función proporcionada no es válida: " + e.getMessage());
        }
        
    }

    public double evaluateFunction(double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }

    public double calculateStep() {
        double h = (b - a) / n;
        return h;
    }

    public double calculateIntegral() {
        double h = calculateStep();
        double sumEven = 0.0;
        double sumOdd = 0.0;

        for (int i = 1; i < n; i++) {
            double x_i = a + i * h;
            if (i % 2 == 0) {
                sumEven += evaluateFunction(x_i);
            } else {
                sumOdd += evaluateFunction(x_i);
            }
        }

        return (h / 3.0) * (evaluateFunction(a) + 4.0 * sumOdd + 2.0 * sumEven + evaluateFunction(b));
    }

    public List<ResultRow> generateTableData() {
        List<ResultRow> dataList = new ArrayList<>();
        double h = calculateStep();
        
        
        double fa = evaluateFunction(a);
        dataList.add(new ResultRow(0, a, fa, 1, fa * 1.0));

        
        for (int i = 1; i < n; i++) {
            double x_i = a + i * h;
            double fx_i = evaluateFunction(x_i);
            
            int weight = (i % 2 == 0) ? 2 : 4; 
            double contribution = fx_i * weight;
            
            dataList.add(new ResultRow(i, x_i, fx_i, weight, contribution));
        }

        
        double fb = evaluateFunction(b);
        dataList.add(new ResultRow(n, b, fb, 1, fb * 1.0));

        return dataList;
    }

    
    public static class ResultRow {
        private final int iteration;
        private final double xi;
        private final double fxi;
        private final int weight;
        private final double contribution;

        public ResultRow(int iteration, double xi, double fxi, int weight, double contribution) {
            this.iteration = iteration;
            this.xi = xi;
            this.fxi = fxi;
            this.weight = weight;
            this.contribution = contribution;
        }

        public int getIteration() {
            return iteration;
        }

        public double getXi() {
            return xi;
        }

        public double getFxi() {
            return fxi;
        }

        public int getWeight() {
            return weight; 
        }

        public double getContribution() {
            return contribution; 
        }
    }
}