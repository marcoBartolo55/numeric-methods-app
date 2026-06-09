package com.numeric.methods.controllers;

import java.io.IOException;
import java.util.List;
import com.numeric.methods.App;
import com.numeric.methods.logic.SecantMethod;
import com.numeric.methods.logic.BisectionMethod.ResultRow; // Reutilización del POJO

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class secantController {

	// Componentes de la vista inyectados por FXML
	@FXML private TextField functionField;
	@FXML private TextField initialGuess1Field;
	@FXML private TextField initialGuess2Field;
	@FXML private TextField iterationsField;
	@FXML private TextField toleranceField;

	@FXML private TableView<ResultRow> resultsTable;
	@FXML private TableColumn<ResultRow, Integer> iterationColumn;
	@FXML private TableColumn<ResultRow, String> xnMinus1Column; // X_{n-1}
	@FXML private TableColumn<ResultRow, String> fxnMinus1Column; // f(X_{n-1})
	@FXML private TableColumn<ResultRow, String> xnPlusColumn;   // X_{n+1}
	@FXML private TableColumn<ResultRow, String> fxnPlusColumn;   // f(X_{n+1})
	@FXML private TableColumn<ResultRow, String> errorColumn;

	// Lista observable mapeada a la tabla
	private ObservableList<ResultRow> tableData = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		iterationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIteration()));
		xnMinus1Column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getA())); // Reutilizando 'A' para X_{n-1}
		fxnMinus1Column.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFa())); // Reutilizando 'Fa' para f(X_{n-1})
		xnPlusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getB())); // Reutilizando 'B' para X_{n+1}
		fxnPlusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFb())); // Reutilizando 'Fb' para f(X_{n+1})
		errorColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getError()));
		resultsTable.setItems(tableData);
	}

	@FXML
	private void calculateSecant() {
		tableData.clear();
	}

	@FXML
	private void switchToMenu() throws IOException {
		App.setRoot("menu-roots-aproximation");
	}

	
}
