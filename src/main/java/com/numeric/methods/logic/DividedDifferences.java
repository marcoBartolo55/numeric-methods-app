package com.numeric.methods.logic;

import java.util.ArrayList;
import java.util.List;

public class DividedDifferences {
    private final double[] x;
    private final double[] y;

    public DividedDifferences(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    public double[][] computeTable() {
        int n = x.length;
        double[][] table = new double[n][n];
        for (int i = 0; i < n; i++) table[i][0] = y[i];

        for (int j = 1; j < n; j++) {
            for (int i = j; i < n; i++) {
                double denominator = x[i] - x[i - j];
                table[i][j] = (Math.abs(denominator) < 1e-12) ? 0 : (table[i][j - 1] - table[i - 1][j - 1]) / denominator;
            }
        }
        return table;
    }

    public String getNewtonPolynomial(double[][] table) {
        StringBuilder sb = new StringBuilder("P(x) = ");
        sb.append(String.format("%.4f", table[0][0]));
        
        for (int i = 1; i < x.length; i++) {
            double coeff = table[i][i];
            sb.append(coeff >= 0 ? " + " : " - ");
            sb.append(String.format("%.4f", Math.abs(coeff)));
            for (int j = 0; j < i; j++) {
                sb.append("(x - ").append(x[j]).append(")");
            }
        }
        return sb.toString();
    }

    public List<ResultRow> generateRows(double[][] table) {
        List<ResultRow> rows = new ArrayList<>();
        for (int i = 0; i < x.length; i++) rows.add(new ResultRow(i, x[i], table[i]));
        return rows;
    }

    public static class ResultRow {
        public double x;
        private final double[] diffs;
        public ResultRow(int i, double x, double[] diffs) { this.x = x; this.diffs = diffs; }
        public Double getDiff(int j) { return (j < diffs.length) ? diffs[j] : null; }
    }
}