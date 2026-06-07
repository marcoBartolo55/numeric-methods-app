package com.numeric.methods.logic;

import java.util.ArrayList;
import java.util.List;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class NewtonRaphsonMethod {
    private final int maxIterations;
    private final double maxError; 
    private final double x0;
    private final Expression expression; 
    private String exitReason = "Se alcanzó el límite máximo de iteraciones.";

    public NewtonRaphsonMethod(double x0, int maxIterations, double maxError, String function) {
        this.x0 = x0;
        this.maxIterations = maxIterations;
        this.maxError = maxError;
        this.expression = new ExpressionBuilder(function).variable("x").build();
    }

    public double evaluateFunction(double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }
    
    public double evaluateDerivative(double x){
        double h = 1e-5;
        expression.setVariable("x", x + h);
        double fXPlusH = expression.evaluate();
    
        expression.setVariable("x", x - h);
        double fXMinusH = expression.evaluate();
        
        return (fXPlusH - fXMinusH) / (2 * h);
    }

    public double calculateNextP(double currentP) {
        double dF = evaluateDerivative(currentP);
        if (Math.abs(dF) < 1e-12) {
            throw new IllegalArgumentException(String.format("La derivada es cercana a cero en x = %.6f. El método diverge.", currentP));
        }
        return currentP - (evaluateFunction(currentP) / dF);
    }

    public String getExitReason() {
        return exitReason;
    }

    // El método execute ahora genera el historial completo para la UI
    public List<ResultRow> execute(boolean useTolerance) {
        List<ResultRow> rows = new ArrayList<>();
        exitReason = "Se alcanzó el límite máximo de iteraciones."; 
        
        double currentP = x0;
        
        for (int iteration = 1; iteration <= maxIterations; iteration++) {
            double fpn = evaluateFunction(currentP);

            // Control de raíz exacta prematura
            if (Math.abs(fpn) < 1e-12) {
                rows.add(new ResultRow(
                    iteration,
                    String.format("%.6f", currentP),
                    String.format("%.6f", fpn),
                    "---",
                    String.format("%.6f", currentP),
                    "0.000000"
                ));
                exitReason = String.format("Se encontró una raíz exacta en x = %.6f", currentP);
                break;
            }

            double ffpn = evaluateDerivative(currentP);
            double nextP = calculateNextP(currentP);
            double error = Math.abs(nextP - currentP);

            rows.add(new ResultRow(
                iteration,
                String.format("%.6f", currentP),
                String.format("%.6f", fpn),
                String.format("%.6f", ffpn),
                String.format("%.6f", nextP),
                String.format("%.6f", error)
            ));

            // Criterios de parada
            if (error == 0.0 || (useTolerance && error <= maxError)) {
                if (useTolerance) {
                    exitReason = String.format("Se alcanzó la tolerancia configurada: %.6f en la iteración %d.", maxError, iteration);
                } else {
                    exitReason = String.format("Convergencia alcanzada con éxito en la iteración %d.", iteration);
                }
                break;
            }

            currentP = nextP;
        }
        return rows;
    }

    // El POJO se aloja en la capa lógica
    public static class ResultRow {
        private final Integer iteration;
        private final String p, fpn, ffpn, pn1, error;

        public ResultRow(int iteration, String p, String fpn, String ffpn, String pn1, String error) {
            this.iteration = iteration;
            this.p = p;
            this.fpn = fpn;
            this.ffpn = ffpn;
            this.pn1 = pn1;
            this.error = error;
        }

        public Integer getIteration() { return iteration; }
        public String getP() { return p; }
        public String getFpn() { return fpn; }
        public String getFfpn() { return ffpn; }
        public String getPn1() { return pn1; }
        public String getError() { return error; }
    }
}