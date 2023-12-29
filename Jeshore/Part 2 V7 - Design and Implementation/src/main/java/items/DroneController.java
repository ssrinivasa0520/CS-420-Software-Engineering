package items;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import static com.example.designandimplementation.Application.controllerCall;


public class DroneController {

    private final DatagramSocket hostSocket;
    private final int dronePort;
    private final InetAddress droneAddress;
    public static DroneController controller;

    public DroneController(int hostPort, int dronePort, String destinationAddress) throws SocketException, UnknownHostException {
        hostSocket = new DatagramSocket(hostPort);
        this.dronePort = dronePort;
        droneAddress = InetAddress.getByName(destinationAddress);
    }

    public String sendCommand(String message, int timeout) throws IOException{
        System.out.println("Command: " + message);
        byte[] data = message.getBytes();
        DatagramPacket call = new DatagramPacket(data, data.length, droneAddress, dronePort);
        int receiveBufferSize = 8192;
        DatagramPacket response = new DatagramPacket(new byte[receiveBufferSize], receiveBufferSize);
        hostSocket.send(call);
        hostSocket.setSoTimeout(timeout);
        try {
            hostSocket.receive(response);
            String textOut = new String(response.getData(), StandardCharsets.UTF_8).trim();
            System.out.println("Response: " + textOut);
            return "Connected";
        }
        catch (SocketTimeoutException e) {
            System.out.println("Timeout reached " + e);
            return "Connection Failed";
        }
    }

    public void closeControlSocket() {
        controllerCall.simulatorButton.setDisable(false);
        controllerCall.launchButton.setDisable(false);
        controllerCall.visitRadioButton.setDisable(false);
        controllerCall.scanRadioButton.setDisable(false);
        hostSocket.close();
        System.out.println("All sockets closed... ");
    }
    public static String test() throws IOException {
        String test = null;
        System.out.println(test);
        controller = new DroneController(9000, 8889, "192.168.10.1");
        System.out.println(test);
        test = controller.sendCommand("command", 100);
        System.out.println(test);
        controllerCall.simulatorButton.setDisable(true);
        controllerCall.launchButton.setDisable(true);
        controllerCall.visitRadioButton.setDisable(true);
        controllerCall.scanRadioButton.setDisable(true);
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