package com.numeric.methods.logic;

public class LagrangeInterpolation {
    private final double[] x;
    private final double[] y;

    public LagrangeInterpolation(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    public double calculate(double xTarget) {
        double result = 0;
        for (int i = 0; i < x.length; i++) {
            double term = y[i];
            for (int j = 0; j < x.length; j++) {
                if (i != j) {
                    term *= (xTarget - x[j]) / (x[i] - x[j]);
                }
            }
            result += term;
        }
        return result;
    }
}