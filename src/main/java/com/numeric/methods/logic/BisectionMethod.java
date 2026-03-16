package com.numeric.methods.logic;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class BisectionMethod {
	int iteration;
	double a, b, error;
	String function;


	public BisectionMethod(double a, double b, int iteration, double error, String function) {
		this.a = a;
		this.b = b;
		this.iteration = iteration;
		this.error = error;
		this.function = function;
	}

	public double calculateMidPoint() {
		double c = (a + b) / 2;
		return c;
	}

	public double evaluateFunction(double x) {
		Expression expression = new ExpressionBuilder(function).variable("x").build();
		expression.setVariable("x", x);
		return expression.evaluate();
	}

	public double changeSign() {
		double c = calculateMidPoint();
		double fa = evaluateFunction(a);
		double fc = evaluateFunction(c);

		if (fc == 0) return c;

		if (Math.signum(fa) == Math.signum(fc)) {
			a = c;
		} else {
			b = c;
		}
		
		return c;
	}

	// Calular errores
	public double currentIterationError() {
		//error <= (b - a) / 2^n
		double errorC = (b - a) / Math.pow(2, iteration);
		return errorC;
	}

    public double iterationsNeededError() {
        // n >= log2((b - a) / error)
        double n = Math.log((b - a) / error) / Math.log(2);
        return Math.ceil(n);
    }

	// Método de bisección
	public double bisection() {
		// Teorema de Bolzano
		if (evaluateFunction(a) * evaluateFunction(b) >= 0) {
			throw new IllegalArgumentException("La función no cambia de signo en el intervalo [a, b].");
		}

		double c = 0;
		int i = 1;

		 while (i < iteration && (b - a) / 2 > error) {
			c = changeSign();
			i++;
		}
		return c;

	}
}

