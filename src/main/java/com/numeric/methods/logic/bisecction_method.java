package com.numeric.methods.logic;

public class bisecction_method {
	public int error_bisection(int a, int b) {
		int error = (b - a) / 2;
		return error;
	}

	public int bisection(int a, int b, int n) {
		int c = (a+b) / 2;
		return c;
	}

	public boolean validation () {
		return true;
	}
}
