module com.example.designandimplementation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.designandimplementation to javafx.fxml;
    exports com.example.designandimplementation;
}