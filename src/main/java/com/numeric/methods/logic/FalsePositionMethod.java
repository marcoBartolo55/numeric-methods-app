package com.numeric.methods.logic;

import java.util.ArrayList;
import java.util.List;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class FalsePositionMethod {
    private final int maxIterations;
    private final double maxError;
    private double x0, x1, xr;
    private final Expression expression;
    private String exitReason = "Se alcanzó el límite máximo de iteraciones.";

    public FalsePositionMethod(double x0, double x1, int maxIterations, double maxError, String function) {
        this.x0 = x0;
        this.x1 = x1;
        this.maxIterations = maxIterations;
        this.maxError = maxError;
        this.expression = new ExpressionBuilder(function).variable("x").build();
    }

    public double evaluateFunction(double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }

    public void validateBolzano() {
        double fx0 = evaluateFunction(x0);
        double fx1 = evaluateFunction(x1);
        if (fx0 * fx1 >= 0) {
            throw new IllegalArgumentException("La función no cambia de signo en el intervalo [" + x0 + ", " + x1 + "].\n" +
                    "(f(x0) y f(x1) tienen el mismo signo; no se puede aplicar Falsa Posición)");
        }
    }

    public double calculateXr() {
        double fx0 = evaluateFunction(x0);
        double fx1 = evaluateFunction(x1);
        xr = x1 - (fx1 * (x0 - x1)) / (fx0 - fx1);
        return xr;
    }

    public void reasignValueBySign() {
        double fx0 = evaluateFunction(x0);
        double fxr = evaluateFunction(xr);

        if (Math.signum(fx0) == Math.signum(fxr)) { 
            x0 = xr; // La raíz está a la derecha, avanza x0
        } else {
            x1 = xr; // La raíz está a la izquierda, se encoge x1
        }
    }

    public String getExitReason() {
        return exitReason;
    }

    /**
     * @param useTolerance true si el criterio de paro es por tolerancia, false si es por iteraciones fijas.
     */
    public List<ResultRow> generateResults(boolean useTolerance) {
        List<ResultRow> rows = new ArrayList<>();
        exitReason = "Se alcanzó el límite máximo de iteraciones."; // Reset por defecto

        
        validateBolzano();

        double prevXr = Double.NaN;
        int iteration = 1;

        while (iteration <= maxIterations) {
            double currentX0 = x0;
            double currentX1 = x1;

            double currentXr = calculateXr();
            double fx0 = evaluateFunction(currentX0);
            double fx1 = evaluateFunction(currentX1);
            double fxr = evaluateFunction(currentXr);


            double currentError = (iteration == 1) ? 0.0 : Math.abs((currentXr - prevXr) / currentXr) * 100;
            prevXr = currentXr;

            String fx0Sign = fx0 > 0 ? "+" : fx0 < 0 ? "-" : "0";
            String fx1Sign = fx1 > 0 ? "+" : fx1 < 0 ? "-" : "0";
            String fxrSign = fxr > 0 ? "+" : fxr < 0 ? "-" : "0";

            rows.add(new ResultRow(
                iteration,
                String.format("%.6f", currentX0),
                String.format("%.6f", currentX1),
                String.format("%.6f", currentXr),
                fx0Sign,
                fx1Sign,
                fxrSign,
                (iteration == 1) ? "—" : String.format("%.6f%%", currentError)
            ));

            
            if (fxr == 0.0 || Math.abs(fxr) < 1e-15) {
                exitReason = String.format("¡Raíz exacta encontrada con éxito en x = %.6f!", currentXr);
                break;
            }

            
            if (iteration > 1 && useTolerance && currentError <= maxError) {
                exitReason = String.format("¡Éxito! Se alcanzó la tolerancia asignada (< %.6f%%) en la iteración %d.", maxError, iteration);
                break;
            }

            
            reasignValueBySign();
            iteration++;
        }
        return rows;
    }

    
    public static class ResultRow {
        private final Integer iteration;
        private final String x0, x1, xr, fx0, fx1, fxr, error;

        public ResultRow(int iteration, String x0, String x1, String xr, String fx0, String fx1, String fxr, String error) {
            this.iteration = iteration;
            this.x0 = x0;
            this.x1 = x1;
            this.xr = xr;
            this.fx0 = fx0;
            this.fx1 = fx1;
            this.fxr = fxr;
            this.error = error;
        }

        public Integer getIteration() {
            return iteration; 
        }

        public String getX0() {
            return x0;
        }

        public String getX1() {
            return x1;
        }

        public String getXr() {
            return xr;
        }

        public String getFx0() {
            return fx0;
        }

        public String getFx1() {
            return fx1;
        }

        public String getFxr() {
            return fxr;
        }

        public String getError() {
            return error;
        }
    }
}