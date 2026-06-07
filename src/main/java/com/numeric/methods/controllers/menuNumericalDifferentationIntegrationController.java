package com.numeric.methods.controllers;

import java.io.IOException;

import com.numeric.methods.App;

import javafx.fxml.FXML;

public class menuNumericalDifferentationIntegrationController {

	@FXML
	private void switchToMain() throws IOException {
		App.setRoot("main");
	}

	@FXML
	private void switchToTaylorPolynomial() throws IOException {
		App.setRoot("taylor-polynomial");
	}

	@FXML
	private void switchToLeastSquares() throws IOException {
		App.setRoot("least-squares");
	}

	@FXML
	private void switchToFunctionApproximation() throws IOException {
		App.setRoot("function-approximation");
	}

	@FXML
	private void switchToDerivative2Points() throws IOException {
		App.setRoot("derivative-2-points");
	}

	@FXML
	private void switchToDerivative3Points() throws IOException {
		App.setRoot("derivative-3-points");
	}
}
