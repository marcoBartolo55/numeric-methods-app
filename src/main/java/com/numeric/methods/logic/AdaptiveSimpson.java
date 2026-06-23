package com.numeric.methods.logic;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class AdaptiveSimpson {
    private final Expression expression;

    public AdaptiveSimpson(String functionStr) {
        try {
            this.expression = new ExpressionBuilder(functionStr).variable("x").build();
        } catch (Exception e) {
            throw new IllegalArgumentException("Función inválida: " + functionStr);
        }
    }

    public double evaluateFunction(double x) {
        if (expression == null) return 0.0;
        expression.setVariable("x", x);
        return expression.evaluate();
    }

    /**
     * Método principal público para iniciar la cuadratura adaptativa.
     * @param a Límite inferior
     * @param b Límite superior
     * @param tolerance Tolerancia máxima de error permitida (Ej: 0.000001)
     */
    public double integrate(double a, double b, double tolerance) {
        double c = (a + b) / 2.0;
        
        double fa = evaluateFunction(a);
        double fb = evaluateFunction(b);
        double fc = evaluateFunction(c);
        
        double globalSimpson = ((b - a) * (fa + 4.0 * fc + fb)) / 6.0;
        
        return adaptiveSimpsonRecursive(a, b, tolerance, globalSimpson, fa, fb, fc);
    }

    private double adaptiveSimpsonRecursive(double a, double b, double tol, double globalSimpson, 
                                            double fa, double fb, double fc) {
        double c = (a + b) / 2.0;
        double d = (a + c) / 2.0;
        double e = (c + b) / 2.0;
        
        double fd = evaluateFunction(d);
        double fe = evaluateFunction(e);
        
        double leftSimpson = ((c - a) * (fa + 4.0 * fd + fc)) / 6.0;
        double rightSimpson = ((b - c) * (fc + 4.0 * fe + fb)) / 6.0;
        
        double fineSimpson = leftSimpson + rightSimpson;
        
        double error = Math.abs(fineSimpson - globalSimpson) / 15.0;
        
        if (error < tol) {
            return fineSimpson + (fineSimpson - globalSimpson) / 15.0;
        }
        
        return adaptiveSimpsonRecursive(a, c, tol / 2.0, leftSimpson, fa, fc, fd) +
               adaptiveSimpsonRecursive(c, b, tol / 2.0, rightSimpson, fc, fb, fe);
    }
}