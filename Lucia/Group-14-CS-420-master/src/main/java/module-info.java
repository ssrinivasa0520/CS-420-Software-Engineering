module com.javafx.farmdashboard {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires lombok;
    requires opencv;
    requires java.logging;
    requires java.desktop;

    opens com.javafx.farmdashboard to javafx.fxml;
    exports com.javafx.farmdashboard;
    opens com.javafx.farmdashboard.components to javafx.fxml;
    exports com.javafx.farmdashboard.components;
    opens com.javafx.farmdashboard.controller to javafx.fxml;
    exports com.javafx.farmdashboard.controller;
    opens com.javafx.farmdashboard.model to javafx.fxml;
    exports com.javafx.farmdashboard.model;
    opens com.javafx.farmdashboard.helpers to javafx.fxml;
    exports com.javafx.farmdashboard.helpers;

}