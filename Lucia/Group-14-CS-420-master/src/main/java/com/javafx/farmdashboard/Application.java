package com.javafx.farmdashboard;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

class DashboardScene extends Scene{

    private static DashboardScene scene;

    private DashboardScene(Parent root, double width, double height) {
        super(root, width, height);
    }

    public static DashboardScene getInstance() throws IOException {
        if(scene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("farm-dashboard.fxml"));
            scene = new DashboardScene(fxmlLoader.load(), 1400, 800);
        }
        return scene;
    }
}

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = DashboardScene.getInstance();
        stage.setTitle("Farm Dashboard");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}