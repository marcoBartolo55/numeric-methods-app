package com.numeric.methods.logic;

import java.util.ArrayList;
import java.util.List;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class TaylorPolynomial {
    private final Expression function;
    private final double a;

    public TaylorPolynomial(String functionStr, double a) {
        try {
            this.function = new ExpressionBuilder(functionStr).variable("x").build();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La función proporcionada no es válida: " + e.getMessage());
        }
        this.a = a;
    }

    private double f(double x) {
        function.setVariable("x", x);
        return function.evaluate();
    }

    public double getDerivative(int order, double x, double h) {
        if (order == 0) return f(x);
        return (getDerivative(order - 1, x + h, h) - getDerivative(order - 1, x - h, h)) / (2.0 * h);
    }

    private double factorial(int n) {
        double fact = 1;
        for (int i = 2; i <= n; i++) fact *= i;
        return fact;
    }

    public List<ResultRow> generateTableData(double x, int n) {
        List<ResultRow> dataList = new ArrayList<>();
        double partialSum = 0;
        double h = 0.001; // Paso para la derivada numérica

        for (int k = 0; k <= n; k++) {
            double derivativeValue = getDerivative(k, a, h);
            double term = (derivativeValue / factorial(k)) * Math.pow(x - a, k);
            
            partialSum += term;
            dataList.add(new ResultRow(k, term, partialSum));
        }
        return dataList;
    }

    public static class ResultRow {
        private final int n;
        private final double termValue;
        private final double partialSum;

        public ResultRow(int n, double termValue, double partialSum) {
            this.n = n;
            this.termValue = termValue;
            this.partialSum = partialSum;
        }

        public int getN() { return n; }
        public double getTermValue() { return termValue; }
        public double getPartialSum() { return partialSum; }
    }
}