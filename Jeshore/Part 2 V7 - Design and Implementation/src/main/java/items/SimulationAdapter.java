package items;

import java.io.IOException;


public class SimulationAdapter implements AdapterInterface{
    FlightControls flightControls;
    int zPos = 0;
    int speed = 10;
    public SimulationAdapter() {
        flightControls = new Drone();
    }
    public void visitItem(ItemInfo selection, String drone) throws IOException, InterruptedException {

        int xPos = (int) (((selection.getLength()/2 + selection.getPosX())/25) * 30.48);
        int yPos = (int) (((selection.getWidth()/2 + selection.getPosY())/25) * 30.48);
        String output = flightControls.test();
        if (output.equalsIgnoreCase("connected")) {
            flightControls.activateSDK();
            flightControls.takeOff();
            flightControls.testBattery();
            flightControls.moveToXYZ(xPos, yPos, zPos, speed);
            flightControls.rotateCCW(360);
            flightControls.hover(5);
            flightControls.moveToXYZ(-xPos, -yPos, zPos, speed);
        }
    }
    public void scanFarm(ItemInfo selection, String drone) throws IOException {
        int xPos = (int) (((selection.getLength()/2 + selection.getPosX())/25) * 30.48);
        int yPos = (int) (((selection.getWidth()/2 + selection.getPosY())/25) * 30.48);
        String output = flightControls.test();
        if (output.equalsIgnoreCase("connected")) {
            flightControls.moveToXYZ(-xPos, -yPos, zPos, speed);
            for (int i = 0; i < 15; i++){
                flightControls.moveForward((int) (24*30.48));
                flightControls.rotateCCW(90);
                flightControls.moveForward((int) 30.48);
                flightControls.rotateCCW(90);
                flightControls.moveForward((int) (24*30.48));
                flightControls.rotateCW(90);
                flightControls.moveForward((int) 30.48);
                flightControls.rotateCW(90);
            }
            flightControls.moveToXYZ(-((int)((800/25)*30.48) - xPos), -((int)((800/25)*30.48) - yPos), zPos, speed);
        }
    }
}




















