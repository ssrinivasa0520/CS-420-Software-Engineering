package items;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    public void hover(int x) throws IOException, InterruptedException {
        if(x > 15){
            testBattery();
            TimeUnit.MICROSECONDS.sleep(14900);
            hover(x - 15);
        } else if (x == 15) {
            testBattery();
            TimeUnit.MICROSECONDS.sleep(14900);
        }else {
            testBattery();
            TimeUnit.SECONDS.sleep(x);
        }
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
    public void moveToXYZ(int x, int y, int z, int speed) throws IOException{
        DroneController.controller.sendCommand(String.format("go %1$d %2$d %3$d %4$d", x, y, z, speed), 15000);
    }
    public void rotateCCW(int x) throws IOException{
        DroneController.controller.sendCommand("ccw" + x, 15000);
    }
    public void rotateCW(int x) throws IOException{
        DroneController.controller.sendCommand("cw" + x, 15000);
    }
    public int testBattery() throws IOException{
        int test = getBattery();
        if (test <= 10 && test > 5){
            JOptionPane.showMessageDialog(null, "Battery less than 10% please charge battery. If battery drops below 5% the drone will land automatically.",
                    "Battery Information", JOptionPane.INFORMATION_MESSAGE);
        }else if(test <= 5){
            JOptionPane.showMessageDialog(null, "Battery less than 5% the drone will land.",
                    "Battery Information", JOptionPane.INFORMATION_MESSAGE);
            endFlight();
        }
        return test;
    }
}
