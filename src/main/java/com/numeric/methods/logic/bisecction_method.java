package com.numeric.methods.logic;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class bisecction_method {
	public double error_bisection(double a, double b) {
		double error = (b - a) / 2;
		return error;
	}

	public double bisection(double a, double b, int n) {
		int maxIter = 0;
		double c = 0;
		while (maxIter < n) {
			c = (a + b) / 2;
			maxIter++;

		}
		return c;
	}

	public boolean validation (int n) {
		if (n > 0) {
			return true;
		}
		return false;

		
	}

	// Evalúa la función matemática en un valor x
	public double evaluateFunction(String function, double x) {
		Expression exp = new ExpressionBuilder(function)
			.variable("x")
			.build()
			.setVariable("x", x);
		return exp.evaluate();
	}
}
