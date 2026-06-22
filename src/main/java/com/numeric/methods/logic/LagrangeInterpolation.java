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
                if (i != j) term *= (xTarget - x[j]) / (x[i] - x[j]);
            }
            result += term;
        }
        return result;
    }

    public String getPolynomialExpression() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < x.length; i++) {
            double li = 1;
            String terms = "";
            for (int j = 0; j < x.length; j++) {
                if (i != j) {
                    li /= (x[i] - x[j]);
                    terms += "(x - " + x[j] + ")";
                }
            }
            double coeff = y[i] * li;
            if (i > 0 && coeff >= 0) sb.append(" + ");
            else if (i > 0) sb.append(" - ");
            else if (coeff < 0) sb.append("-");
            
            sb.append(String.format("%.2f", Math.abs(coeff))).append(terms);
        }
        return sb.toString();
    }
}