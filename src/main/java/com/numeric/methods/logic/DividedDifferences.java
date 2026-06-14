package com.numeric.methods.logic;

// TODO: Contruir el polinomio

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
                if (Math.abs(denominator) < 1e-12) {
                    table[i][j] = Double.NaN;
                } else {
                    table[i][j] = (table[i][j - 1] - table[i - 1][j - 1]) / denominator;
                }
            }
        }
        return table;
    }

    public List<ResultRow> generateRows(double[][] table) {
        List<ResultRow> rows = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            rows.add(new ResultRow(i, x[i], table[i]));
        }
        return rows;
    }

    public static class ResultRow {
        private final int i;
        private final double x;
        private final double[] diffs;

        public ResultRow(int i, double x, double[] diffs) {
            this.i = i;
            this.x = x;
            this.diffs = diffs;
        }

        public double getX() { return x; }
        public Double getDiff(int j) { 
            return (j <= i && !Double.isNaN(diffs[j])) ? diffs[j] : null; 
        }
    }
}