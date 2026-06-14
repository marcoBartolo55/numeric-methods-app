package com.numeric.methods.logic;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Romberg {
    private final Expression function;

    public Romberg(String functionStr) {
        this.function = new ExpressionBuilder(functionStr).variable("x").build();
    }

    private double f(double x) {
        function.setVariable("x", x);
        return function.evaluate();
    }

    // Regla del trapecio compuesta
    private double trapezoid(double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.5 * (f(a) + f(b));
        for (int i = 1; i < n; i++) sum += f(a + i * h);
        return sum * h;
    }

    public double[][] computeRomberg(double a, double b, int n) {
        double[][] R = new double[n][n];
        
        // Columna 0: Regla del trapecio con 2^i subintervalos
        for (int i = 0; i < n; i++) {
            R[i][0] = trapezoid(a, b, (int) Math.pow(2, i));
            
            // Extrapolación de Richardson
            for (int j = 1; j <= i; j++) {
                R[i][j] = R[i][j - 1] + (R[i][j - 1] - R[i - 1][j - 1]) / (Math.pow(4, j) - 1);
            }
        }
        return R;
    }
}