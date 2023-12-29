package items;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Drone implements FlightControls{ //drone commands implements FlightControls

    public String test() throws IOException { //Tests connection
        return DroneController.test();
    }

    public int getBattery() throws IOException { //gets battery life
        try {
            return Integer.parseInt(DroneController.controller.sendCommand("battery?", 15000));
        } catch (NumberFormatException e) {
            System.out.println("Number Format Exception due to timeout! " + e);
            return 0;
        }
    }
    public void takeOff() throws IOException { //takeoff command
        DroneController.controller.sendCommand("takeoff", 15000);
    }
    public void landDrone() throws IOException { //land command
        DroneController.controller.sendCommand("land", 15000);
    }
    public void endFlight() { //Closes socket
        DroneController.controller.closeControlSocket();
        System.out.println("Flight Ended...");
    }
    public void hover(int x) throws IOException, InterruptedException { //tells drone to wait
        if(x > 15){
            printBattery();
            TimeUnit.MICROSECONDS.sleep(14900);
            hover(x - 15);
        } else if (x == 15) {
            printBattery();
            TimeUnit.MICROSECONDS.sleep(14900);
        }else {
            printBattery();
            TimeUnit.SECONDS.sleep(x);
        }
    }
    public void moveForward(int x) throws IOException{ //forward command
        DroneController.controller.sendCommand("forward " + x, 15000);
    }
    public void moveToXYZ(int x, int y, int z, int speed) throws IOException{ //move command
        DroneController.controller.sendCommand(String.format("go %1$d %2$d %3$d %4$d", x, y, z, speed), 15000);
    }
    public void rotateCCW(int x) throws IOException{ //counterclockwise command
        DroneController.controller.sendCommand("ccw " + x, 15000);
    }
    public void rotateCW(int x) throws IOException{ //clockwise command
        DroneController.controller.sendCommand("cw " + x, 15000);
    }
    public void testBattery() throws IOException{ //test battery and let user know
        int test = getBattery();
        if (test <= 20 && test > 10){
            JOptionPane.showMessageDialog(null, "Battery less than 20% please charge battery. If battery drops below 10% the drone will land automatically.",
                    "Battery Information", JOptionPane.INFORMATION_MESSAGE);
        }else if(test <= 10){
            JOptionPane.showMessageDialog(null, "Battery less than 10%. Please change battery.",
                    "Battery Information", JOptionPane.INFORMATION_MESSAGE);
            endFlight();
        }
    }
    public void printBattery() throws IOException{ // print battery percent
        int test = getBattery();
        if (test <= 20 && test > 10){
            System.out.println("Battery Percentage: " + test + ". If percentage drops below 10% the drone will land and not take off.");
        }else if(test <= 10){
            JOptionPane.showMessageDialog(null, "Battery less than 10% the drone will land.",
                    "Battery Information", JOptionPane.INFORMATION_MESSAGE);
            landDrone();
            endFlight();
        }
    }
}
