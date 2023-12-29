package com.javafx.farmdashboard.components;

import com.javafx.farmdashboard.model.Item;
import com.javafx.farmdashboard.model.ItemI;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class FarmVisualizationComponent {

    private AnchorPane farmVisualization;

    private ImageView drone;

    public FarmVisualizationComponent(AnchorPane pane, ImageView drone) {
        farmVisualization = pane;
        this.drone = drone;
    }

    public void render(ItemI item) {
        farmVisualization.getChildren().clear();
        renderRecursive(item);
    }

    public void renderRecursive(ItemI item) {

        if(item.isDrone()) {
            drawDrone(item);
        } else {
            drawItem(item);
        }

        item.getChildren().forEach(this::renderRecursive);
    }

    private void drawItem(ItemI item) {
        Rectangle rect = new Rectangle(item.getPosX(), item.getPosY(), item.getWidth(), item.getLength());
        rect.setAccessibleText(item.getName());
        rect.setStroke(Color.BLACK);
        rect.setFill(item.isContainer() ? Color.TRANSPARENT :Color.WHITE);
        rect.setStrokeWidth(item.isContainer() ? 3 : 2);
        rect.setViewOrder(0);

        farmVisualization.getChildren().add(rect);
        drawLabel(item);
    }

    private void drawDrone(ItemI item) {
        drone.setX(item.getPosX());
        drone.setY(item.getPosY());
        drone.setFitHeight(item.getWidth());
        drone.setFitWidth(item.getLength());
        drone.setVisible(true);
        drone.setViewOrder(-1);

        farmVisualization.getChildren().add(drone);
        //drawLabel(item);
    }

    private void drawLabel(ItemI item) {
        Text text = new Text(item.getPosX(), item.getPosY() + item.getLength() + 15, item.getName());
        text.setAccessibleText(item.getName());
        text.setFill(Color.BLACK);

        farmVisualization.getChildren().add(text);
    }
}
