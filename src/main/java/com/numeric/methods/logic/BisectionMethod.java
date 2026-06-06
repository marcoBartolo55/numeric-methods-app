package com.numeric.methods.logic;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class BisectionMethod {
    int iteration;
    double a, b, error;
    private final Expression expression;

    public BisectionMethod(double a, double b, int iteration, double error, String function) {
        this.a = a;
        this.b = b;
        this.iteration = iteration;
        this.error = error;
        this.expression = new ExpressionBuilder(function).variable("x").build();
    }

    public double calculateMidPoint() {
        return a + (b - a) / 2;
    }

    public double evaluateFunction(double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }

    public double changeSign() {
        double c = calculateMidPoint();
        double fa = evaluateFunction(a);
        double fc = evaluateFunction(c);

        if (Math.abs(fc) < 1e-15) return c;

        if (Math.signum(fa) == Math.signum(fc)) {
            a = c;
        } else {
            b = c;
        }
        
        return c;
    }

    public double currentIterationError() {
        return (b - a) / Math.pow(2, iteration);
    }

    public double iterationsNeededError() {
        double n = Math.log((b - a) / error) / Math.log(2);
        return Math.ceil(n);
    }

    public double bisection() {
        double fa = evaluateFunction(a);
        double fb = evaluateFunction(b);

        if (Math.abs(fa) < 1e-15) return a;
        if (Math.abs(fb) < 1e-15) return b;

        if (Math.signum(fa) == Math.signum(fb)) {
            throw new IllegalArgumentException("Error: No se garantiza una raíz en el intervalo [" + a + ", " + b + "] porque f(a) y f(b) tienen el mismo signo.");
        }

        double c = calculateMidPoint();
        int i = 1;

        while (i <= iteration && ((b - a) / 2.0) > error) {
            c = changeSign();
            i++;
        }
        return c;
    }
}