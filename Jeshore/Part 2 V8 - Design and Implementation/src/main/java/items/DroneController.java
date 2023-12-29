package items;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import static com.example.designandimplementation.Application.controllerCall;
public class DroneController { //Controller to connect and send commands to the drone
    //Variables to connect to drone
    private final DatagramSocket hostSocket;
    private final int dronePort;
    private final InetAddress droneAddress;
    public static DroneController controller;

    public DroneController(int hostPort, int dronePort, String destinationAddress) throws SocketException, UnknownHostException { // gets addresses and creates new sockets to connect to drone
        hostSocket = new DatagramSocket(hostPort);
        this.dronePort = dronePort;
        droneAddress = InetAddress.getByName(destinationAddress);
    }

    public String sendCommand(String message, int timeout) throws IOException{ // send command to drone
        DatagramPacket response = command(message, timeout);
        try {
            hostSocket.receive(response);
            String textOut = new String(response.getData(), StandardCharsets.UTF_8).trim();
            System.out.println("Response: " + textOut);
            return textOut;
        }
        catch (SocketTimeoutException e) {
            System.out.println("Timeout reached " + e);
            return "Connection Failed";
        }
    }
    public String testCommand(String message, int timeout) throws IOException{ // tests connection throws error if not
        DatagramPacket response = command(message, timeout);
        try {
            hostSocket.receive(response);
            String textOut = new String(response.getData(), StandardCharsets.UTF_8).trim();
            System.out.println("Response: " + textOut);
            return "Connected";
        }
        catch (SocketTimeoutException e) {
            System.out.println("Timeout reached " + e);
            JOptionPane.showMessageDialog(null, "Drone not connected.",
                    "Drone Error", JOptionPane.INFORMATION_MESSAGE);
            return "Connection Failed";
        }
    }

    private DatagramPacket command(String message, int timeout) throws IOException { // send command to drone
        System.out.println("Command: " + message);
        byte[] data = message.getBytes();
        DatagramPacket call = new DatagramPacket(data, data.length, droneAddress, dronePort);
        int receiveBufferSize = 8192;
        DatagramPacket response = new DatagramPacket(new byte[receiveBufferSize], receiveBufferSize);
        hostSocket.send(call);
        hostSocket.setSoTimeout(timeout);
        return response;
    }

    public void closeControlSocket() { //close socket
        controllerCall.simulationRadioButton.setDisable(false);
        controllerCall.droneRadioButton.setDisable(false);
        controllerCall.startButton.setDisable(false);
        controllerCall.treeView.setDisable(false);
        if (controllerCall.treeView.getSelectionModel().getSelectedItem().getValue().getCommandCenter() == 1){
            controllerCall.startButton.setText("Scan Farm");
        }
        hostSocket.close();
        System.out.println("All sockets closed... ");
    }
    public static String test() throws IOException {  //Test connection to drone
        String test;
        controller = new DroneController(9000, 8889, "192.168.10.1");
        test = controller.testCommand("command", 100);
        controllerCall.simulationRadioButton.setDisable(true);
        controllerCall.droneRadioButton.setDisable(true);
        controllerCall.startButton.setDisable(true);
        controllerCall.treeView.setDisable(true);
        if (controllerCall.treeView.getSelectionModel().getSelectedItem().getValue().getCommandCenter() == 1){
            controllerCall.startButton.setText("Scanning Farm...");
        }
        if (test.equalsIgnoreCase("connected")){
            return test;
        }else if (test.equalsIgnoreCase("connection Failed")){
            controller.closeControlSocket();
            return test;
        }else {
            return test;
        }
    }
}