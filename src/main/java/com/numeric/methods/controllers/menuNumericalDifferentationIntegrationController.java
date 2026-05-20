package com.numeric.methods.controllers;

import java.io.IOException;

import com.numeric.methods.App;

import javafx.fxml.FXML;

public class menuNumericalDifferentationIntegrationController {

	@FXML
	private void switchToMain() throws IOException {
		App.setRoot("main");
	}
}
