package com.numeric.methods.logic;

import java.util.ArrayList;
import java.util.List;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class BisectionMethod {
    private double a, b;
    private final int maxIterations;
    private final double tolerance;
    private final Expression expression;
    private String exitReason = "Se alcanzó el límite máximo de iteraciones.";

    public BisectionMethod(double a, double b, int maxIterations, double tolerance, String function) {
        this.a = a;
        this.b = b;
        this.maxIterations = maxIterations;
        this.tolerance = tolerance;
        this.expression = new ExpressionBuilder(function).variable("x").build();
    }

    public double evaluateFunction(double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }

    public String getExitReason() {
        return exitReason;
    }

    /**
     * @param useIterations
     * @return
     */
    public List<ResultRow> generateResults(boolean useIterations) {
        List<ResultRow> rows = new ArrayList<>();
        exitReason = "Se alcanzó el límite máximo de iteraciones."; // Reset por defecto

        double fa0 = evaluateFunction(a);
        double fb0 = evaluateFunction(b);

        // Validaciones del Teorema de Bolzano antes de iterar
        if (fa0 == 0.0 || fb0 == 0.0) return rows; 
        if (fa0 * fb0 > 0) {
            throw new IllegalArgumentException("Intervalo no válido: la función no cambia de signo en [a, b].\n" +
                    "(f(a) y f(b) tienen el mismo signo; no se puede aplicar Bisección con ese intervalo)");
        }

        int totalIterations = useIterations ? maxIterations : (int) iterationsNeededError();
        double prevC = Double.NaN;

        for (int i = 1; i <= totalIterations; i++) {
            double fa = evaluateFunction(a);
            double fb = evaluateFunction(b);
            double c = a + (b - a) / 2.0; // Cálculo de punto medio numéricamente estable
            double fc = evaluateFunction(c);
            
            // Error absoluto aproximado (0.0 en la primera iteración)
            double currentError = (i == 1) ? 0.0 : Math.abs(c - prevC);
            prevC = c;

            // Formatear los signos para mejor impacto visual en la UI
            String faSign = fa > 0 ? "+" : fa < 0 ? "-" : "0";
            String fbSign = fb > 0 ? "+" : fb < 0 ? "-" : "0";
            String fcSign = fc > 0 ? "+" : fc < 0 ? "-" : "0";

            
            rows.add(new ResultRow(
                i,
                String.format("%.6f", a),
                String.format("%.6f", b),
                String.format("%.6f", c),
                faSign,
                fbSign,
                fcSign,
                String.format("%.6f", currentError)
            ));

            if (fc == 0.0) {
                exitReason = String.format("¡Raíz exacta encontrada con éxito en x = %.6f!", c);
                break;
            }
            
            if (!useIterations && Math.abs(fc) < tolerance) {
                exitReason = String.format("¡Éxito! Se alcanzó la tolerancia asignada (< %.6f) en la iteración %d.", tolerance, i);
                break;
            }

            // Desplazamiento de las paredes por análisis de signos
            if (Math.signum(fa) == Math.signum(fc)) {
                a = c;
            } else {
                b = c;
            }
        }
        return rows;
    }

    private double iterationsNeededError() {
        if (tolerance <= 0) return maxIterations;
        double n = Math.log((b - a) / tolerance) / Math.log(2);
        return Math.ceil(n);
    }

    public static class ResultRow {
        private final Integer iteration;
        private final String a, b, aproximacion, fa, fb, fc, error;

        public ResultRow(int iteration, String a, String b, String aproximacion, String fa, String fb, String fc, String error) {
            this.iteration = iteration;
            this.a = a;
            this.b = b;
            this.aproximacion = aproximacion;
            this.fa = fa;
            this.fb = fb;
            this.fc = fc;
            this.error = error;
        }

        public Integer getIteration() { return iteration; }
        public String getA() { return a; }
        public String getB() { return b; }
        public String getAproximacion() { return aproximacion; }
        public String getFa() { return fa; }
        public String getFb() { return fb; }
        public String getFc() { return fc; }
        public String getError() { return error; }
    }
}