package items;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Drone implements FlightControls{

    private final DroneController controller;

    public Drone() throws SocketException, UnknownHostException, FileNotFoundException {
        this.controller = new DroneController(3702, 8889, "192.168.10.1");

    }

    public void activateSDK() throws IOException {
        this.controller.sendCommand("command");
    }

    public void end() throws IOException, InterruptedException {
        this.controller.closeControlSocket();
        System.out.println("Exit Program...");
    }
    public void takeoff() throws IOException {

    }

    public void land() throws IOException {

    }

    public void increaseAltitude(int up) throws IOException {

    }

    public void decreaseAltitude(int down) throws IOException {

    }

    public void flyForward(int front) throws IOException {

    }

    public void flyLeft(int left) throws IOException {

    }

    public void flyRight(int right) throws IOException {

    }

    public void turnCW(int degrees) throws IOException {

    }

    public void flyBackward(int back) throws IOException {

    }

    public void hoverInPlace(int seconds) throws InterruptedException, IOException {

    }

    public int getHeight() throws IOException {
        return 0;
    }

    public int getAttitudePitch() throws IOException {
        return 0;
    }

    public int getAttitudeRoll() throws IOException {
        return 0;
    }

    public int getAttitudeYaw() throws IOException {
        return 0;
    }

    public double getAccelerationX() throws IOException {
        return 0;
    }

    public double getAccelerationY() throws IOException {
        return 0;
    }

    public double getAccelerationZ() throws IOException {
        return 0;
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        Drone tello = new Drone();

        System.out.println("Tello Drone Demo" + "\n");
        System.out.println("Tello: command takeoff land flip forward back left right" + "\n" + "      " + " up down cw ccw speed speed?" + "\n");
        System.out.println("end -- quit demo" + "\n");

        Scanner scan = new Scanner(System.in);

        String command = scan.nextLine();

        while(!command.equals("end") && command != null && !command.trim().isEmpty()) {
            tello.controller.sendCommand(command);
            command = scan.nextLine();
        }

        scan.close();
        tello.end();
        //tello.controller.closeControlSocket();
        System.out.println("Exit Program...");
    }
}
