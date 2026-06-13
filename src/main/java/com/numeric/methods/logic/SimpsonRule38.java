package com.numeric.methods.logic;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class SimpsonRule38 {
    private double b, a;
    private final Expression expression;

    public SimpsonRule38(String function, double a, double b) {
        this.a = a;
        this.b = b;
        this.expression = new ExpressionBuilder(function).variable("x").build();
    }

    public double evaluateFunction(double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }

    public double midPoint1() {
        double c1 = a + calculateStep();
        return c1;
    }

    public double midPoint2() {
        double c2 = a + 2.0 * calculateStep();
        return c2;
    }

    public double calculateStep() {
        double h = (b - a) / 3.0;
        return h;
    }

    public double calculateIntegral() {
        return ((3.0 * calculateStep()) * (evaluateFunction(a) + 3.0 * evaluateFunction(midPoint1()) + 3.0 * evaluateFunction(midPoint2()) + evaluateFunction(b))) / 8.0;
    }
}
