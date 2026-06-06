package com.numeric.methods.logic;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class NewtonRaphsonMethod {
    private final int maxIterations;
    private final double maxError; // Tolerancia pedida por el usuario
    private double pn;
    private final Expression expression; // Compilamos la función una sola vez

    
    public NewtonRaphsonMethod(double x0, int maxIterations, double maxError, String function) {
        this.pn = x0;
        this.maxIterations = maxIterations;
        this.maxError = maxError;
        this.expression = new ExpressionBuilder(function).variable("x").build();
    }

    // Calcula el siguiente punto basándose en un p_n dado
    public double calculateNextP(double currentP) {
        double dF = evaluateDerivative(currentP);
        
        if (Math.abs(dF) < 1e-12) {
            throw new IllegalArgumentException("La derivada es cercana a cero en x = " + currentP + ". El método diverge.");
        }
        
        return currentP - (evaluateFunction(currentP) / dF);
    }

    public double evaluateFunction(double x) {
        expression.setVariable("x", x);
        return expression.evaluate();
    }
    
    public double evaluateDerivative(double x){
        double h = 1e-5;
        
        expression.setVariable("x", x + h);
        double fXPlusH = expression.evaluate();
    
        expression.setVariable("x", x - h);
        double fXMinusH = expression.evaluate();
        
        return (fXPlusH - fXMinusH) / (2 * h);
    }

    // Método principal para ejecutar el algoritmo iterativo
    public double execute() {
        int currentIteration = 0;
        double calculatedError = Double.MAX_VALUE;

        System.out.println("Iteración 0: p0 = " + pn);

        // El ciclo se detiene si alcanza las iteraciones máximas O si el error es menor al pedido
        while (currentIteration < maxIterations && calculatedError > maxError) {
            double nextPn = calculateNextP(pn);
            
            // Error relativo/absoluto entre el punto nuevo y el anterior
            calculatedError = Math.abs(nextPn - pn); 
            
            pn = nextPn; // Actualizamos nuestro punto actual para la siguiente iteración
            currentIteration++;

            System.out.printf("Iteración %d: pn = %.6f | Error = %.6e%n", currentIteration, pn, calculatedError);
            
            // Opcional: Detener si f(pn) ya es prácticamente 0
            if (Math.abs(evaluateFunction(pn)) < 1e-15) {
                break;
            }
        }

        return pn;
    }
}