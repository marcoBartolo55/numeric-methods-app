package com.numeric.methods.controllers;

import java.io.IOException;

import com.numeric.methods.App;

import javafx.fxml.FXML;

public class menuOrdinaryDifferentialEquationsController {

	@FXML
	private void switchToMain() throws IOException {
		App.setRoot("main");
	}

	@FXML
	private void switchToRichardsonExtrapolation() throws IOException {
		App.setRoot("richardson-extrapolation");
	}

	@FXML
	private void switchToTrapezoidalRule() throws IOException {
		App.setRoot("trapezoidal-rule");
	}

	@FXML
	private void switchToSimpson13() throws IOException {
		App.setRoot("simpson-rule-13");
	}

	@FXML
	private void switchToSimpson38() throws IOException {
		App.setRoot("simpson-rule-38");
	}

	@FXML
	private void switchToMultipleSimpson() throws IOException {
		App.setRoot("multiple-simpson");
	}

	@FXML
	private void switchToRomberg() throws IOException {
		App.setRoot("romberg");
	}

	@FXML
	private void switchToAdaptiveSimpson() throws IOException {
		App.setRoot("adaptive-simpson");
	}

	@FXML
	private void switchToEulerMethod() throws IOException {
		App.setRoot("euler");
	}

	@FXML
	private void switchToRungeKutta2() throws IOException {
		App.setRoot("runge-kutta-2");
	}

	@FXML
	private void switchToRungeKutta3() throws IOException {
		App.setRoot("runge-kutta-3");
	}

	@FXML
	private void switchToRungeKutta4() throws IOException {
		App.setRoot("runge-kutta-4");
	}

	@FXML
	private void switchToRungeKuttaFehlberg() throws IOException {
		App.setRoot("runge-kutta-fehlberg");
	}

	@FXML
	private void switchToGaussianQuadrature() throws IOException {
		App.setRoot("gaussian-quadrature");
	}

}

