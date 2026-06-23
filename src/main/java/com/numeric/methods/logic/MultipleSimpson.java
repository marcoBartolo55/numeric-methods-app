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
            throw new IllegalArgumentException("Función no válida: " + e.getMessage());
        }
    }

    public double evaluateFunction(double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }

    public double calculateStep() {
        return (b - a) / n;
    }

    public double calculateIntegral13() {
        double h = calculateStep();
        double sumEven = 0.0, sumOdd = 0.0;
        for (int i = 1; i < n; i++) {
            double x_i = a + i * h;
            if (i % 2 == 0) sumEven += evaluateFunction(x_i);
            else sumOdd += evaluateFunction(x_i);
        }
        return (h / 3.0) * (evaluateFunction(a) + 4.0 * sumOdd + 2.0 * sumEven + evaluateFunction(b));
    }

    public double calculateIntegral38() {
        double h = calculateStep();
        double sum = 0.0;
        for (int i = 1; i < n; i++) {
            double x_i = a + i * h;
            int weight = (i % 3 == 0) ? 2 : 3;
            sum += weight * evaluateFunction(x_i);
        }
        return (3.0 * h / 8.0) * (evaluateFunction(a) + sum + evaluateFunction(b));
    }

    public List<ResultRow> generateTableData(boolean is38) {
        List<ResultRow> data = new ArrayList<>();
        double h = calculateStep();
        data.add(new ResultRow(0, a, evaluateFunction(a), 1, evaluateFunction(a)));
        for (int i = 1; i < n; i++) {
            double x_i = a + i * h;
            int weight = is38 ? ((i % 3 == 0) ? 2 : 3) : ((i % 2 == 0) ? 2 : 4);
            data.add(new ResultRow(i, x_i, evaluateFunction(x_i), weight, evaluateFunction(x_i) * weight));
        }
        data.add(new ResultRow(n, b, evaluateFunction(b), 1, evaluateFunction(b)));
        return data;
    }

    public static class ResultRow {
        private final int iteration;
        private final double xi, fxi, contribution;
        private final int weight;

        public ResultRow(int iteration, double xi, double fxi, int weight, double contribution) {
            this.iteration = iteration;
            this.xi = xi;
            this.fxi = fxi;
            this.weight = weight;
            this.contribution = contribution;
        }

        public int getIteration() { return iteration; }
        public double getXi() { return xi; }
        public double getFxi() { return fxi; }
        public int getWeight() { return weight; }
        public double getContribution() { return contribution; }
    }
}