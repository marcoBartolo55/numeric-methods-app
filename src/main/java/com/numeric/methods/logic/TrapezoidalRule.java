package com.numeric.methods.logic;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
public class TrapezoidalRule {
    private double a;
    private double b;
    private int n; // Número de trapecios
    private final Expression expression;

    public TrapezoidalRule(String function, double a, double b) {
        this.a = a;
        this.b = b;
        try {
            this.expression = new ExpressionBuilder(function).variable("x").build();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La función proporcionada no es válida: " + e.getMessage());
        }
    }

    public TrapezoidalRule(String function, int n, double a, double b) {
        this.n = n;
        this.a = a;
        this.b = b;
        try {
            this.expression = new ExpressionBuilder(function).variable("x").build();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La función proporcionada no es válida: " + e.getMessage());
        }
    }

    public double evaluateFunction(double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }

    public double calculateSimpleTrapezoidal() {
        return  ((b - a) * (evaluateFunction(a) + evaluateFunction(b))) / 2.0;
    }

    public double calculateCompositeTrapezoidal() {
        if (n <= 0) {
            throw new IllegalArgumentException("El número de trapecios debe ser mayor que cero.");
        }

        double h = (b - a) / n;
        double sum = 0.0;

        for (int i = 1; i < n; i++) {
            sum += evaluateFunction(a + i * h);
        }
        return (h / 2.0) * (evaluateFunction(a) + (2.0 * sum) + evaluateFunction(b));
    }
}
