package com.numeric.methods.logic;

import java.util.ArrayList;
import java.util.List;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class FixPointMethod {
    private final int maxIterations;
    private final double maxError;
    private double x0;
    private final Expression expression;
    private String exitReason = "Se alcanzó el límite máximo de iteraciones.";

    public FixPointMethod(double x0, int maxIterations, double maxError, String funcion) {
        this.x0 = x0;
        this.maxIterations = maxIterations;
        this.maxError = maxError;
        this.expression = new ExpressionBuilder(funcion).variable("x").build();
    }

    public double evaluateFunction(double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }

        public void calculateNextX() {
            x0 = evaluateFunction(x0);
        }

    public String getExitReason() {
        return exitReason;
    }
    
    /**
     * @param useTolerance true si el criterio de paro es por tolerancia, false si es por iteraciones fijas.
     */

    public List<ResultRow> generateResults(boolean useTolerance) {
        List<ResultRow> results = new ArrayList<>();
        exitReason = "Se alcanzó el límite máximo de iteraciones.";
        
        double currentX = x0;  // Usar variable local
        
        for (int i = 1; i <= maxIterations; i++) {
            double nextX = evaluateFunction(currentX);
            double error = Math.abs(nextX - currentX);
            results.add(new ResultRow(i, currentX, nextX, error));
            
            if (useTolerance && error < maxError) {
                exitReason = "Se alcanzó la tolerancia deseada.";
                break;
            }
            currentX = nextX;  // Actualizar para siguiente iteración
        }
        return results;
    }

    public static class ResultRow {
        private final Integer iteration;
        private final Double x0;
        private final Double gxn;
        private final Double error;

        public ResultRow(Integer iteration, Double x0, Double gxn, Double error) {
            this.iteration = iteration;
            this.x0 = x0;
            this.gxn = gxn;
            this.error = error;
        }

        public Integer getIteration() {
            return iteration;
        }

        public Double getX0() {
            return x0;
        }

        public Double getGxn() {
            return gxn;
        }

        public Double getError() {
            return error;
        }
    }
    
}
