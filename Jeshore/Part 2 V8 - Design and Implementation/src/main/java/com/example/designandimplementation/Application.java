package com.example.designandimplementation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application { //Starts Program
    public static Controller controllerCall;
    public static Scene scene;
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("dashboard.fxml"));
        scene = new Scene(fxmlLoader.load());
        controllerCall = fxmlLoader.getController();
        stage.setTitle("Farm Dashboard");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { //Main Function
        launch();
    }
}