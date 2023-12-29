package items;


import javafx.animation.PathTransition;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.io.IOException;

import static com.example.designandimplementation.Application.controllerCall;
import static com.example.designandimplementation.Controller.*;


public class Simulation implements AdapterInterface {
    SimulationAdapter simulationAdapter;
    public void visitItem(ItemInfo selection, String drone) throws IOException, InterruptedException { //Gets path to item on farm and animates the drone to move along that path
        if(drone.equalsIgnoreCase("notReal")){
            if(selection.getLength()/2 + selection.getPosX() == xPos && selection.getWidth()/2 + selection.getPosY() == yPos){
                return;
            }
            Path scan = new Path();
            System.out.println(xPos);
            System.out.println(yPos);
            scan.getElements().add(new MoveTo(xPos, yPos));
            scan.getElements().add(new LineTo(selection.getLength()/2 + selection.getPosX(), selection.getWidth()/2 + selection.getPosY()));
            PathTransition scantransition = new PathTransition();
            scantransition.setNode(controllerCall.drone);
            scantransition.setDuration(Duration.millis(3000));
            scantransition.setPath(scan);
            scantransition.setCycleCount(1);
            scantransition.play();
            xPos = selection.getLength()/2 + selection.getPosX();
            yPos = selection.getWidth()/2 + selection.getPosY();
            controllerCall.simulatorButton.setDisable(true);
            controllerCall.launchButton.setDisable(true);
            controllerCall.visitRadioButton.setDisable(true);
            controllerCall.scanRadioButton.setDisable(true);
            scantransition.setOnFinished(event -> {
                controllerCall.simulatorButton.setDisable(false);
                controllerCall.launchButton.setDisable(false);
                controllerCall.visitRadioButton.setDisable(false);
                controllerCall.scanRadioButton.setDisable(false);
            });

        }else if (drone.equalsIgnoreCase("real")){
            simulationAdapter = new SimulationAdapter();
            simulationAdapter.visitItem(selection, drone);
        }
    }

    public void scanFarm(ItemInfo selection, String drone) throws IOException { //animates drone to scan the entirety of the farm
        if(drone.equalsIgnoreCase("notReal")) {
            int point = 600;
            if (droneCount == 0) {
                return;
            }
            Path scan = new Path();
            scan.getElements().add(new MoveTo(xPos, yPos));
            scan.getElements().add(new LineTo(25, 25));
            while (true) {
                point = point - 25;
                scan.getElements().add(new VLineTo(point));
                scan.getElements().add(new HLineTo(775));
                if (point == 25) {
                    break;
                }
                point = point - 25;
                scan.getElements().add(new VLineTo(point));
                scan.getElements().add(new HLineTo(25));
            }
            scan.getElements().add(new VLineTo(selection.getWidth() / 2 + selection.getPosY()));
            scan.getElements().add(new HLineTo(selection.getLength() / 2 + selection.getPosX()));
            PathTransition scantransition = new PathTransition();
            scantransition.setNode(controllerCall.drone);
            scantransition.setDuration(Duration.millis(15000));
            scantransition.setPath(scan);
            scantransition.setCycleCount(1);
            scantransition.play();
            controllerCall.simulatorButton.setDisable(true);
            controllerCall.launchButton.setDisable(true);
            controllerCall.visitRadioButton.setDisable(true);
            controllerCall.scanRadioButton.setDisable(true);
            controllerCall.simulatorButton.setText("Scanning Farm...");
            controllerCall.launchButton.setText("Scanning Farm...");
            scantransition.setOnFinished(event -> {
                controllerCall.simulatorButton.setDisable(false);
                controllerCall.launchButton.setDisable(false);
                controllerCall.visitRadioButton.setDisable(false);
                controllerCall.scanRadioButton.setDisable(false);
                controllerCall.simulatorButton.setText("Scan Farm");
                controllerCall.launchButton.setText("Scan Farm");
            });
            xPos = selection.getLength() / 2 + selection.getPosX();
            yPos = selection.getWidth() / 2 + selection.getPosY();
        }else if (drone.equalsIgnoreCase("real")){
            simulationAdapter = new SimulationAdapter();
            simulationAdapter.scanFarm(selection, drone);
        }
    }
}
