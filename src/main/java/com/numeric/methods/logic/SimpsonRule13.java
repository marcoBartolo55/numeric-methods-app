package com.numeric.methods.logic;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class SimpsonRule13 {
    private double b, a;
    private final Expression expression;

    public SimpsonRule13(String function, double a, double b) {
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

    public double midPoint() {
        double c = (a + b) / 2.0;
        return c;
    }

    public double calculateStep() {
        double h = (b - a) / 2.0;
        return h;
    }

    public double calculateIntegral() {
        double h = calculateStep();
        double c = midPoint();
        double fa = evaluateFunction(a);
        double fb = evaluateFunction(b);
        double fc = evaluateFunction(c);
        return (h / 3.0) * (fa + 4.0 * fc + fb);
    }

}
