package items;


import javafx.animation.PathTransition;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.io.IOException;

import static com.example.designandimplementation.Application.controllerCall;
import static com.example.designandimplementation.Controller.*;


public class Simulation implements AdapterInterface { //simulation call
    SimulationAdapter simulationAdapter;
    public void visitItem(ItemInfo selection, String drone) throws IOException, InterruptedException { //Gets path to item on farm and animates the drone to move along that path
        if(drone.equalsIgnoreCase("notReal")){
            if(selection.getLength()/2 + selection.getPosX() == xPos && selection.getWidth()/2 + selection.getPosY() == yPos){
                return;
            }
            controllerCall.simulationRadioButton.setDisable(true);
            controllerCall.droneRadioButton.setDisable(true);
            controllerCall.startButton.setDisable(true);
            controllerCall.treeView.setDisable(true);
            Path scan = new Path();
            scan.getElements().add(new MoveTo(droneXPos, droneYPos));
            scan.getElements().add(new LineTo(selection.getLength()/2 + selection.getPosX(), selection.getWidth()/2 + selection.getPosY()));
            PathTransition scanTransition = new PathTransition();
            scanTransition.setNode(controllerCall.drone);
            scanTransition.setDuration(Duration.millis(3000));
            scanTransition.setPath(scan);
            scanTransition.setCycleCount(1);
            scanTransition.play();
            scanTransition.setOnFinished(event -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("e");
                    throw new RuntimeException(e);
                }
                Path scan2 = new Path();
                scan2.getElements().add(new MoveTo(selection.getLength()/2 + selection.getPosX(), selection.getWidth()/2 + selection.getPosY()));
                scan2.getElements().add(new LineTo(droneXPos, droneYPos));
                PathTransition scanTransition2 = new PathTransition();
                scanTransition2.setNode(controllerCall.drone);
                scanTransition2.setDuration(Duration.millis(3000));
                scanTransition2.setPath(scan2);
                scanTransition2.setCycleCount(1);
                scanTransition2.play();
                scanTransition2.setOnFinished(event2 -> {
                    controllerCall.simulationRadioButton.setDisable(false);
                    controllerCall.droneRadioButton.setDisable(false);
                    controllerCall.startButton.setDisable(false);
                    controllerCall.treeView.setDisable(false);
                });
            });

        }else if (drone.equalsIgnoreCase("real")){ //calls drone
            simulationAdapter = new SimulationAdapter();
            simulationAdapter.visitItem(selection, drone);
        }
    }

    public void scanFarm(ItemInfo selection, String drone) throws IOException { //animates drone to scan the entirety of the farm
        if(drone.equalsIgnoreCase("notReal")) {
            int point = 0;
            if (droneCount == 0) {
                return;
            }
            Path scan = new Path();
            scan.getElements().add(new MoveTo(xPos, yPos));
            scan.getElements().add(new LineTo(25, 25));
            while (true) {
                point = point + 25;
                scan.getElements().add(new HLineTo(point));
                scan.getElements().add(new VLineTo(575));
                if(point == 775){
                    break;
                }
                point = point + 25;
                scan.getElements().add(new HLineTo(point));
                scan.getElements().add(new VLineTo(25));
            }
            scan.getElements().add(new LineTo(selection.getLength()/2 + selection.getPosX(), selection.getWidth()/2 + selection.getPosY()));
            PathTransition scantransition = new PathTransition();
            scantransition.setNode(controllerCall.drone);
            scantransition.setDuration(Duration.millis(15000));
            scantransition.setPath(scan);
            scantransition.setCycleCount(1);
            scantransition.play();
            controllerCall.simulationRadioButton.setDisable(true);
            controllerCall.droneRadioButton.setDisable(true);
            controllerCall.startButton.setDisable(true);
            controllerCall.treeView.setDisable(true);
            controllerCall.startButton.setText("Scanning Farm...");
            scantransition.setOnFinished(event -> {
                controllerCall.simulationRadioButton.setDisable(false);
                controllerCall.droneRadioButton.setDisable(false);
                controllerCall.startButton.setDisable(false);
                controllerCall.treeView.setDisable(false);
                controllerCall.startButton.setText("Scan Farm");
            });
            xPos = selection.getLength() / 2 + selection.getPosX();
            yPos = selection.getWidth() / 2 + selection.getPosY();
        }else if (drone.equalsIgnoreCase("real")){ //calls drone
            simulationAdapter = new SimulationAdapter();
            simulationAdapter.scanFarm(selection, drone);
        }
    }
}
