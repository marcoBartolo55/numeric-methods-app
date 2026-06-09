package com.numeric.methods.logic;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
public class TrapezoidalRule {
    private double a;
    private double b;
    private int n; // Número de trapecios
    private boolean type;
    private final Expression expression;

    public TrapezoidalRule(String function, double a, double b, double n, boolean type) {
        this.a = a;
        this.b = b;
        this.n = (int) n;
        this.type = type;
        this.expression = new ExpressionBuilder(function).variable("x").build();
    }


    public double evaluateFunction(double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }
}
