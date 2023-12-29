package com.javafx.farmdashboard.helpers;

import com.javafx.farmdashboard.Constants;
import com.javafx.farmdashboard.components.DroneComponent;
import com.javafx.farmdashboard.controller.TelloDroneController;
import com.javafx.farmdashboard.model.Coordinates;
import javafx.animation.KeyValue;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class DroneAnimator extends TelloDroneController implements TelloDroneAdapter {

    private final Node drone;

    public DroneAnimator(ImageView drone) {
        this.drone = drone;
    }

    public Path getPathForFarmScan(double initPosX, double initPosY, double finalPosX, double finalPosY, double MAX_X_BOUND, double MAX_Y_BOUND) {
        Path path = new Path();
        ObservableList<PathElement> pathElements = path.getElements();

        pathElements.add(new MoveTo(initPosX, initPosY));

        for(int i = 25; i <= MAX_X_BOUND; i += 25) {
            pathElements.add(new HLineTo(initPosX + i));
            pathElements.add(new VLineTo(MAX_Y_BOUND));
            i += 25;
            pathElements.add(new HLineTo(initPosY + i));
            pathElements.add(new VLineTo(initPosY));
        }

        pathElements.add(new LineTo(finalPosX, finalPosY));

        return path;
    }

    public Path getPathToCoordinates(double initPosX, double initPosY, double finalPosX, double finalPosY) {
        Path path = new Path();
        ObservableList<PathElement> pathElements = path.getElements();
        pathElements.add(new MoveTo(initPosX, initPosY));
        pathElements.add(new LineTo(finalPosX, finalPosY));
        return path;
    }

    public PathTransition getPathTransition(Path path, double duration, int cycleCount) {
        PathTransition transition = new PathTransition();
        transition.setNode(drone);
        transition.setDuration(Duration.millis(duration));
        transition.setPath(path);
        transition.setCycleCount(cycleCount);

        return transition;
    }

    @Deprecated
    public SequentialTransition getFarmScanSequentialTransition(double initPosX, double initPosY, double MIN_X_BOUND, double MAX_X_BOUND, double MIN_Y_BOUND, double MAX_Y_BOUND, int cycleCount) {

        List<Timeline> timelineList = new ArrayList<>();

        timelineList.add(new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(drone.translateXProperty(), MIN_X_BOUND)), new KeyFrame(Duration.seconds(1), new KeyValue(drone.translateYProperty(), MIN_Y_BOUND))));

        for(double posY = MIN_Y_BOUND + 25; posY <= MAX_Y_BOUND;) {
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(drone.rotateProperty(), drone.getRotate() - 90))));
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(drone.translateXProperty(), MAX_X_BOUND))));
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(drone.rotateProperty(), drone.getRotate() - 90))));
            posY += 25;
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(drone.translateYProperty(), posY))));
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(drone.rotateProperty(), drone.getRotate() - 90))));
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(drone.translateXProperty(), MIN_X_BOUND))));
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(drone.rotateProperty(), drone.getRotate() - 90))));
            posY += 25;
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(drone.translateYProperty(), posY))));
        }

        for(double posY = MAX_Y_BOUND - 25; posY >= MIN_Y_BOUND;) {
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(0.5), new KeyValue(drone.rotateProperty(), drone.getRotate() - 90))));
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(drone.translateXProperty(), MAX_X_BOUND))));
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(0.5), new KeyValue(drone.rotateProperty(), drone.getRotate() - 90))));
            posY -= 25;
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(drone.translateYProperty(), posY))));
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(0.5), new KeyValue(drone.rotateProperty(), drone.getRotate() - 90))));
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(drone.translateXProperty(), MIN_X_BOUND))));
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(0.5), new KeyValue(drone.rotateProperty(), drone.getRotate() - 90))));
            posY -= 25;
            timelineList.add(new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(drone.translateYProperty(), posY - 25))));
        }

        timelineList.add(new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(drone.translateXProperty(), initPosX)), new KeyFrame(Duration.seconds(1), new KeyValue(drone.translateYProperty(), initPosY))));

        // Create Sequence
        SequentialTransition sequence = new SequentialTransition();
        sequence.getChildren().addAll(timelineList);
        // Let the animation run forever
        sequence.setCycleCount(cycleCount);

        return sequence;
    }

    @Override
    public void scanFarm(Coordinates finalCoordinates, EventHandler<ActionEvent> onStart, EventHandler<ActionEvent> onFinish) {
        double initPosX = 60;
        double initPosY = 60;
        double finalPosX = finalCoordinates.getXAfterOffset();
        double finalPosY = finalCoordinates.getYAfterOffset();
        double droneWidth = DroneComponent.droneItem.getWidth();
        double droneLength = DroneComponent.droneItem.getLength();
        Path path = getPathForFarmScan(initPosX, initPosY, finalPosX, finalPosY, initPosX + Constants.FARM_WIDTH - droneWidth, initPosY + Constants.FARM_LENGTH - droneLength);
        PathTransition transition = getPathTransition(path, 15000, 1);
        transition.play();
        onStart.handle(null);
        transition.setOnFinished(onFinish);
    }

    @Deprecated
    public void scanFarmV2(Coordinates finalCoordinates, EventHandler<ActionEvent> onStart, EventHandler<ActionEvent> onFinish) {
        double initPosX = DroneComponent.droneItem.getPosX();
        double initPosY = DroneComponent.droneItem.getPosY();
        double finalPosX = finalCoordinates.getXAfterOffset();
        double finalPosY = finalCoordinates.getYAfterOffset();
        double droneWidth = DroneComponent.droneItem.getWidth();
        double droneLength = DroneComponent.droneItem.getLength();
        SequentialTransition sequence = getFarmScanSequentialTransition(initPosX, initPosY, 40,40 + Constants.FARM_WIDTH - droneWidth, 40, 40 + Constants.FARM_LENGTH - droneLength, 1);
        sequence.play();
        onStart.handle(null);
        sequence.setOnFinished(onFinish);
    }

    @Override
    public void visitItem(Coordinates itemCoordinates, EventHandler<ActionEvent> onStart, EventHandler<ActionEvent> onFinish) {
        double initPosX = DroneComponent.droneItem.getPosX() + itemCoordinates.getOffsetX();
        double initPosY = DroneComponent.droneItem.getPosY() + itemCoordinates.getOffsetY();
        double finalPosX = itemCoordinates.getXAfterOffset();
        double finalPosY = itemCoordinates.getYAfterOffset();
        Path path1 = getPathToCoordinates(initPosX, initPosY, finalPosX, finalPosY);
        Path path2 = getPathToCoordinates(finalPosX, finalPosY, initPosX, initPosY);
        PathTransition transition1 = getPathTransition(path1, 2000, 1);
        PathTransition transition2 = getPathTransition(path2, 2000, 1);
        transition1.play();
        onStart.handle(null);
        transition1.setOnFinished((e) -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            transition2.play();
            transition2.setOnFinished(onFinish);
        });
    }

}
