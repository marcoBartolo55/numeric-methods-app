package com.numeric.methods.logic;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.IterativeLegendreGaussIntegrator;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class GaussianQuadrature {

    /**
     * Resuelve la integral definida usando Cuadratura de Gauss-Legendre.
     */
    public static double integrate(String functionString, double a, double b, int n) {
        if (n < 2) {
            throw new IllegalArgumentException("El número de puntos (n) debe ser al menos 2 para usar Cuadratura Gaussiana.");
        }

        // Convertimos el String a una función evaluable
        UnivariateFunction f = new UnivariateFunction() {
            @Override
            public double value(double x) {
                Expression exp = new ExpressionBuilder(functionString).variable("x").build();
                exp.setVariable("x", x);
                return exp.evaluate();
            }
        };

        // Configuramos el integrador de Apache Commons Math
        IterativeLegendreGaussIntegrator integrator = new IterativeLegendreGaussIntegrator(
            n, 
            IterativeLegendreGaussIntegrator.DEFAULT_RELATIVE_ACCURACY, 
            IterativeLegendreGaussIntegrator.DEFAULT_ABSOLUTE_ACCURACY
        );

        // Realizamos el cálculo
        return integrator.integrate(Integer.MAX_VALUE, f, a, b);
    }

    public static class IntegrationResult {
        private final String function, a, b, n, result;

        public IntegrationResult(String function, double a, double b, int n, double result) {
            this.function = function;
            this.a = String.format("%.4f", a);
            this.b = String.format("%.4f", b);
            this.n = String.valueOf(n);
            this.result = String.format("%.6f", result); // Mostramos 6 decimales para alta precisión
        }

        public String getFunction() { return function; }
        public String getA() { return a; }
        public String getB() { return b; }
        public String getN() { return n; }
        public String getResult() { return result; }
    }
}