package com.numeric.methods.logic;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
public class NewtonRaphsonMethod {
    int iteration;
    double pn, error;
    String function;

    public NewtonRaphsonMethod(double pn, int iteration, double error, String function) {
        this.pn = pn;
        this.iteration = iteration;
        this.error = error;
        this.function = function;
    }


    public double calculatePnPlusOne() {
        if (evaluateDerivative(pn) != 0) {
            double fPn = pn - (evaluateFunction(pn) / evaluateDerivative(pn));
            return fPn;
        } else {
            throw new IllegalArgumentException("La derivada es cero en el punto actual. No se puede continuar con el método de Newton-Raphson.");
        }
    }

    public double evaluateFunction(double x) {
        Expression expression = new ExpressionBuilder(function).variable("x").build();
        expression.setVariable("x", x);
        return expression.evaluate();
    }
    
    public double evaluateDerivative(double x){
        Expression expression = new ExpressionBuilder(function).variable("x").build();
        double h = 1e-5;

        expression.setVariable("x", x + h);
        double fXPlusH = expression.evaluate();
    
        expression.setVariable("x", x - h);
        double fXMinusH = expression.evaluate();
        
        return (fXPlusH - fXMinusH) / (2 * h);
    }

    public double currentError() {
        for (int i = 0; i < iteration; i++) {
            pn = calculatePnPlusOne();
        }
        int currentIteration = 0;
        double errorC = Math.abs(pn - calculatePnPlusOne());
        if (errorC == error && currentIteration == 0) {
            return errorC;
        }
        else {
            error = errorC;
            return error;
        }
    }

    
}
