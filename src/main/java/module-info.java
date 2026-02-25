module com.numeric.methods {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.numeric.methods to javafx.fxml;
    opens com.numeric.methods.controllers to javafx.fxml;
    exports com.numeric.methods;
    exports com.numeric.methods.controllers;
    exports com.numeric.methods.logic;
    
}
