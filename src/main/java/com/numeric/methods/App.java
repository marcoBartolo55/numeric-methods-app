package com.numeric.methods;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"), 1000, 700);
        scene.getStylesheets().add(App.class.getResource("/com/numeric/methods/style/main.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Proyecto Métodos Numéricos");
        stage.setResizable(false);
        stage.show();
    }

        public static void setRoot(String fxml) throws IOException {
            Parent root = loadFXML(fxml);
            scene.setRoot(root);
            scene.getStylesheets().clear();
            
            String cssPath = "/com/numeric/methods/style/" + fxml.replace("-", "-") + ".css";
            if (App.class.getResource(cssPath) != null) {
                scene.getStylesheets().add(App.class.getResource(cssPath).toExternalForm());
            } else {
                if (App.class.getResource("/com/numeric/methods/style/main.css") != null) {
                    scene.getStylesheets().add(App.class.getResource("/com/numeric/methods/style/main.css").toExternalForm());
                }
            }
        }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/numeric/methods/view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}