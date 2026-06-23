package com.numeric.methods.logic;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.complex.Complex;

public class NewtonRaphsonMethod {
    private final int maxIterations;
    private final double maxError;
    private Complex x0;
    private String exitReason = "Se alcanzó el límite máximo de iteraciones.";

    public NewtonRaphsonMethod(Complex x0, int maxIterations, double maxError) {
        this.x0 = x0;
        this.maxIterations = maxIterations;
        this.maxError = maxError;
    }

    // Definición de f(z). Ejemplo: f(z) = z^2 + 1
    public Complex evaluateFunction(Complex z) {
        return z.multiply(z).add(Complex.ONE);
    }

    // Definición de f'(z). Ejemplo: f'(z) = 2z
    public Complex evaluateDerivative(Complex z) {
        return z.multiply(2.0);
    }

    public List<ResultRow> execute(boolean useTolerance) {
        List<ResultRow> rows = new ArrayList<>();
        Complex currentP = x0;

        for (int i = 1; i <= maxIterations; i++) {
            Complex fpn = evaluateFunction(currentP);
            Complex ffpn = evaluateDerivative(currentP);

            if (ffpn.abs() < 1e-12) throw new IllegalArgumentException("Derivada cercana a cero.");

            Complex nextP = currentP.subtract(fpn.divide(ffpn));
            double error = nextP.subtract(currentP).abs();

            rows.add(new ResultRow(i, currentP, fpn, ffpn, nextP, error));

            if (useTolerance && error <= maxError) {
                exitReason = "Convergencia alcanzada.";
                break;
            }
            currentP = nextP;
        }
        return rows;
    }

    public String getExitReason() { return exitReason; }

    public static class ResultRow {
        private final int iteration;
        private final Complex p, fpn, ffpn, pn1;
        private final double error;

        public ResultRow(int it, Complex p, Complex fpn, Complex ffpn, Complex pn1, double err) {
            this.iteration = it;
            this.p = p; this.fpn = fpn; this.ffpn = ffpn;
            this.pn1 = pn1; this.error = err;
        }
        public Integer getIteration() { return iteration; }
        public String getP() { return p.toString(); }
        public String getFpn() { return fpn.toString(); }
        public String getFfpn() { return ffpn.toString(); }
        public String getPn1() { return pn1.toString(); }
        public String getError() { return String.format("%.6f", error); }
    }
}