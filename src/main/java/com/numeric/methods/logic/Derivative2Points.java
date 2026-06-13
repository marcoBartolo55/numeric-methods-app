package com.numeric.methods.logic;

import java.util.ArrayList;
import java.util.List;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Derivative2Points {
    private double x0, h;
    private Expression expression;
    private List<Point> points;

    public Derivative2Points(String function, double x0, double h) {
        this.x0 = x0;
        this.h = h;
        this.expression = new ExpressionBuilder(function).variable("x").build();
    }

    public Derivative2Points(List<Point> points) {
        this.points = points;
    }

    public double evaluateFunction(double x) {
        if (expression == null) return 0.0;
        expression.setVariable("x", x);
        return expression.evaluate();
    }

    public double calculateBackward() {
        return (evaluateFunction(x0) - evaluateFunction(x0 - h)) / h;
    }

    public double calculateCentral() {
        return (evaluateFunction(x0 + h) - evaluateFunction(x0 - h)) / (2.0 * h);
    }
    
    public double calculateForward() {
        return (evaluateFunction(x0 + h) - evaluateFunction(x0)) / h;
    }

    public List<ResultRow> generateTableData() {
        List<ResultRow> dataList = new ArrayList<>();
        
        if (points == null || points.size() < 2) {
            return dataList;
        }

        dataList.add(new ResultRow(0, points.get(0).getX(), points.get(0).getY(), 0.0));

        for (int i = 1; i < points.size(); i++) {
            Point pAnt = points.get(i - 1); 
            Point pAct = points.get(i);     
            
            double deltaX = pAct.getX() - pAnt.getX();
            double deltaY = pAct.getY() - pAnt.getY();
            
            double derivativeApprox = (deltaX != 0) ? (deltaY / deltaX) : 0.0;
            
            dataList.add(new ResultRow(i, pAct.getX(), pAct.getY(), derivativeApprox));
        }
        
        return dataList;
    }

    public static class Point {
        private final double x;
        private final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    public static class ResultRow {
        private final int iteration;
        private final double xi;
        private final double yi;
        private final double derivative;

        public ResultRow(int iteration, double xi, double yi, double derivative) {
            this.iteration = iteration;
            this.xi = xi;
            this.yi = yi;
            this.derivative = derivative;
        }

        public int getIteration() {
            return iteration;
        }

        public double getXi() {
            return xi;
        }

        public double getYi() {
            return yi;
        }

        public double getDerivative() {
            return derivative;
        }
    }
}