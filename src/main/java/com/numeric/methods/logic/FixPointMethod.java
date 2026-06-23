package com.numeric.methods.logic;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.complex.Complex;

public class FixPointMethod {
    private final int maxIterations;
    private final double maxError;
    private final Complex x0;
    private String exitReason = "Se alcanzó el límite máximo de iteraciones.";

    public FixPointMethod(Complex x0, int maxIterations, double maxError) {
        this.x0 = x0;
        this.maxIterations = maxIterations;
        this.maxError = maxError;
    }

    // Lógica de g(x) = x^2 + 1 (Ajusta aquí según necesites)
    public Complex evaluateFunction(Complex x) {
        return x.multiply(x).add(Complex.ONE);
    }

    public List<ResultRow> generateResults(boolean useTolerance) {
        List<ResultRow> results = new ArrayList<>();
        Complex currentX = x0;
        
        for (int i = 1; i <= maxIterations; i++) {
            Complex nextX = evaluateFunction(currentX);
            double error = nextX.subtract(currentX).abs();
            results.add(new ResultRow(i, currentX, nextX, error));
            
            if (useTolerance && error < maxError) {
                exitReason = "Se alcanzó la tolerancia: " + maxError;
                break;
            }
            currentX = nextX;
        }
        return results;
    }

    public String getExitReason() { return exitReason; }

    public static class ResultRow {
        private final Integer iteration;
        private final String x0, gxn;
        private final Double error;

        public ResultRow(Integer iteration, Complex x0, Complex gxn, Double error) {
            this.iteration = iteration;
            this.x0 = formatComplex(x0);
            this.gxn = formatComplex(gxn);
            this.error = error;
        }

        private String formatComplex(Complex c) {
            return String.format("%.4f + %.4fi", c.getReal(), c.getImaginary());
        }

        public Integer getIteration() { return iteration; }
        public String getX0() { return x0; }
        public String getGxn() { return gxn; }
        public Double getError() { return error; }
    }
}