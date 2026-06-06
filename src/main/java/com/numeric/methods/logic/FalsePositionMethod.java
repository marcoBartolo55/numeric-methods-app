package com.numeric.methods.logic;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class FalsePositionMethod {
    int iteration;
    double x0, x1, xr, error;
    String function;

    public FalsePositionMethod(double x0, double x1, double xr, int iteration, double error, String function) {
        this.x0 = x0;
        this.x1 = x1;
        this.xr = xr;
        this.iteration = iteration;
        this.error = error;
        this.function = function;
    }

    public double getX0() {
        return x0;
    }

    public double getX1() {
        return x1;
    }

    public double evaluateFunction(double x) {
        Expression expression = new ExpressionBuilder(function).variable("x").build();
        expression.setVariable("x", x);
        return expression.evaluate();
    }

    public double reasignValueBySign() {
        double fx0 = evaluateFunction(x0);
        double fx1 = evaluateFunction(x1);
        
        double newXr = x1 - (evaluateFunction(x1) * (x0 - x1)) / (evaluateFunction(x0) - evaluateFunction(x1));
        double fnxt = evaluateFunction(newXr);

        if (Math.signum(fx0) == Math.signum(fnxt)) {
            x0 = newXr;
        } else {
            x1 = newXr;
        }
        
        return newXr;
    }

    public double currentIterationError(double oldXr) {
        if (xr == 0) return 100;
        error = Math.abs((xr - oldXr) / xr);
        return error;
    }

    public double falsePosition() {
        // 1. Teorema de Bolzano inicial
        if (evaluateFunction(x0) * evaluateFunction(x1) >= 0) {
            throw new IllegalArgumentException("La función no cambia de signo en el intervalo [x0, x1].");
        }

        int i = 0;
        while (i < iteration) {
            double oldXr = xr;
            
            xr = reasignValueBySign();
            
            currentIterationError(oldXr);

            i++;

            if (Math.abs(evaluateFunction(xr)) < 1e-9) break;
        }
        return xr;
    }
}