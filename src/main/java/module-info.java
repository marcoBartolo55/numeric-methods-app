module com.numeric.methods {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.numeric.methods to javafx.fxml;
    exports com.numeric.methods;
}
