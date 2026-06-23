module com.numeric.methods {
    // Dependencias de JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    
    // Dependencias de cálculo matemático (Módulos automáticos)
    requires commons.math3;
    requires exp4j;

    // Abrir los paquetes a JavaFX para que FXML pueda inyectar los @FXML
    opens com.numeric.methods to javafx.fxml;
    opens com.numeric.methods.controllers to javafx.fxml;

    // Exportar los paquetes para que sean visibles
    exports com.numeric.methods;
    exports com.numeric.methods.controllers;
    exports com.numeric.methods.logic;
}