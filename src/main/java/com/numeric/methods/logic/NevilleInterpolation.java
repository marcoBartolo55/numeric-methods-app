package com.numeric.methods.logic;

import java.util.ArrayList;
import java.util.List;

public class NevilleInterpolation {
    private final double[] xValues;
    private final double[] yValues;

    public NevilleInterpolation(double[] xValues, double[] yValues) {
        this.xValues = xValues;
        this.yValues = yValues;
    }

    public List<ResultRow> generateTableData(double xTarget) {
        int n = xValues.length;
        double[][] P = new double[n][n];

        
        for (int i = 0; i < n; i++) P[i][0] = yValues[i];

        
        for (int j = 1; j < n; j++) {
            for (int i = j; i < n; i++) {
                P[i][j] = ((xTarget - xValues[i - j]) * P[i][j - 1] 
                         - (xTarget - xValues[i]) * P[i - 1][j - 1]) 
                         / (xValues[i] - xValues[i - j]);
            }
        }

        
        List<ResultRow> dataList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            dataList.add(new ResultRow(xValues[i], P[i]));
        }
        return dataList;
    }

    public static class ResultRow {
        private final double x;
        private final double[] pValues; // Contiene toda la fila de la tabla

        public ResultRow(double x, double[] pValues) {
            this.x = x;
            this.pValues = pValues;
        }

        public double getX() { return x; }
        public double getP(int j) { 
            return (j < pValues.length) ? pValues[j] : 0.0; 
        }
    }
}