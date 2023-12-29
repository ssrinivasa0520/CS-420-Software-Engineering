package items;

import java.io.IOException;
import static com.example.designandimplementation.Controller.*;
public class SimulationAdapter implements AdapterInterface{ //calls drone
    FlightControls flightControls;
    int zPos = 0;
    int speed = 75;
    public SimulationAdapter() {
        flightControls = new Drone();
    } //allows call to drone commands through interface
    public void visitItem(ItemInfo selection, String drone) throws IOException, InterruptedException { //drone command to visit item
        int dXPos = (int) (droneXPos / 25 * 30.48);
        int dYPos = (int) (droneYPos / 25 * 30.48);
        int xPos = (int) ((((selection.getWidth() / 2 + selection.getPosY()) / 25) * 30.48) - dYPos);
        int yPos = (int) ((((selection.getLength() / 2 + selection.getPosX()) / 25) * 30.48) - dXPos);
        String output = flightControls.test();
        if (output.equalsIgnoreCase("connected")) {
            flightControls.testBattery();
            flightControls.takeOff();
            if (xPos > 500 || yPos > 500) {
                flightControls.moveToXYZ(xPos / 2, yPos / 2, zPos, speed);
                flightControls.moveToXYZ(xPos / 2, yPos / 2, zPos, speed);
            } else {
                flightControls.moveToXYZ(xPos, yPos, zPos, speed);
            }
            flightControls.rotateCCW(360);
            flightControls.hover(5);
            if (xPos > 500 || yPos > 500) {
                flightControls.moveToXYZ(-xPos / 2, -yPos / 2, zPos, speed);
                flightControls.moveToXYZ(-xPos / 2, -yPos / 2, zPos, speed);
            } else {
                flightControls.moveToXYZ(-xPos, -yPos, zPos, speed);
            }
            flightControls.landDrone();
            flightControls.endFlight();
        }
    }
    public void scanFarm(ItemInfo selection, String drone) throws IOException { //drone command to scan farm
        int dXPos = (int) (droneXPos / 25 * 30.48);
        int dYPos = (int) (droneYPos / 25 * 30.48);
        int xPos = (int) ((((selection.getWidth() / 2 + selection.getPosY()) / 25) * 30.48));
        int yPos = (int) ((((selection.getLength() / 2 + selection.getPosX()) / 25) * 30.48));
        String output = flightControls.test();
        if (output.equalsIgnoreCase("connected")) {
            flightControls.testBattery();
            flightControls.takeOff();
            if (xPos > 500 || yPos > 500) {
                flightControls.moveToXYZ(-dYPos / 2, -dXPos / 2, zPos, speed);
                flightControls.moveToXYZ(-dYPos / 2, -dXPos / 2, zPos, speed);
            } else {
                flightControls.moveToXYZ(-dYPos, -dXPos, zPos, speed);
            }
            for (int i = 0; i <= 7; i++) {
                flightControls.testBattery();
                flightControls.moveForward((int) ((24 * 30.48) / 2));
                flightControls.moveForward((int) ((24 * 30.48) / 2));
                flightControls.rotateCCW(90);
                flightControls.moveForward((int) (30.48 * 2));
                flightControls.rotateCCW(90);
                flightControls.moveForward((int) ((24 * 30.48) / 2));
                flightControls.moveForward((int) ((24 * 30.48) / 2));
                flightControls.rotateCW(90);
                flightControls.moveForward((int) (30.48 * 2));
                flightControls.rotateCW(90);
            }
            flightControls.moveForward((int) ((24 * 30.48) / 2));
            flightControls.moveForward((int) ((24 * 30.48) / 2));
            if (-(((int) ((24) * 30.48) - dYPos)) < -500 || -(((int) ((800 / 25) * 30.48) - dXPos)) < -500) {
                flightControls.moveToXYZ(-(((int) ((24) * 30.48) - dYPos)) / 2, -(((int) ((32) * 30.48) - dXPos)) / 2, zPos, speed);
                flightControls.moveToXYZ(-(((int) ((24) * 30.48) - dYPos)) / 2, -(((int) ((32) * 30.48) - dXPos)) / 2, zPos, speed);
            } else {
                flightControls.moveToXYZ(-(((int) ((600 / 25) * 30.48) - dYPos)), -(((int) ((800 / 25) * 30.48) - dXPos)), zPos, speed);
            }
            flightControls.landDrone();
            flightControls.endFlight();
        }
    }
}




















