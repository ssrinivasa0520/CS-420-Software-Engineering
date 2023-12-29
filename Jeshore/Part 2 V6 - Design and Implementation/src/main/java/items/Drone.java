package items;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
public class Drone implements FlightControls{

    public String test() throws IOException {
        return DroneController.test();
    }

    public int getBattery() throws IOException {
        try {
            return Integer.parseInt(DroneController.controller.sendCommand("battery?", 15000));
        } catch (NumberFormatException e) {
            System.out.println("Number Format Exception due to timeout! " + e);
            return 0;
        }
    }

    public void activateSDK() throws IOException {
        DroneController.controller.sendCommand("command", 15000);
    }
    public void takeOff() throws IOException {
        DroneController.controller.sendCommand("takeoff", 15000);
    }
    public void landDrone() throws IOException {
        DroneController.controller.sendCommand("land", 15000);
    }
    public void endFlight() {
        DroneController.controller.closeControlSocket();
        System.out.println("Flight Ended...");
    }
    public void moveBack(int x) throws IOException{
        DroneController.controller.sendCommand("back " + x, 15000);
    }
    public void moveForward(int x) throws IOException{
        DroneController.controller.sendCommand("forward " + x, 15000);
    }
    public void moveLeft(int x) throws IOException{
        DroneController.controller.sendCommand("left " + x, 15000);
    }
    public void moveRight(int x) throws IOException{
        DroneController.controller.sendCommand("right " + x, 15000);
    }

}
