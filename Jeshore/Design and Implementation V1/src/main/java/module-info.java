module com.example.designandimplementation {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.designandimplementation to javafx.fxml;
    exports com.example.designandimplementation;
}