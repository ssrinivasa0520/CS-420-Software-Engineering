package items;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class DroneController {

    private DatagramSocket hostSocket;
    private int dronePort;
    private final int receiveBufferSize = 8192;
    private InetAddress droneAddress;

    public DroneController(int hostPort, int dronePort, String destinationAddress) throws SocketException, UnknownHostException {
        hostSocket = new DatagramSocket(hostPort);
        this.dronePort = dronePort;
        droneAddress = InetAddress.getByName(destinationAddress);
    }

    public String sendCommand(String msg) throws IOException{
        System.out.println("Sending command: " + msg);
        byte[] data = msg.getBytes();
        String output = "";
        DatagramPacket call = new DatagramPacket(data, data.length, droneAddress, dronePort);
        DatagramPacket response = new DatagramPacket(new byte[receiveBufferSize], receiveBufferSize);
        hostSocket.send(call);
        hostSocket.setSoTimeout(15000);
        try {
            hostSocket.receive(response);
            output = new String(response.getData(), "UTF-8").trim();
            System.out.println("Incoming response: " + output);
        }
        catch (SocketTimeoutException e) {
            System.out.println("Timeout reached " + e);
            return "Timeout";
        }
        return output;
    }

    public void closeControlSocket() {
        hostSocket.close();
        System.out.println("All sockets closed... ");
    }

    public DatagramSocket getHostSocket() {
        return hostSocket;
    }

    public int getDronePort() {
        return dronePort;
    }

    public int getReceiveBufferSize() {
        return receiveBufferSize;
    }

    public InetAddress getDroneAddress() {
        return droneAddress;
    }

    public static void main(String[] args) throws IOException {
        DroneController tester = new DroneController(3702, /*11111,*/ 8889, "192.168.10.1");

        System.out.println("Drone Controller Demo" + "\n");
        System.out.println("Try any string to test" + "\n");
        System.out.println("end -- quit demo" + "\n");

        Scanner scan = new Scanner(System.in);

        String command = scan.nextLine();

        while(!command.equals("end") && !command.trim().isEmpty()) {
            tester.sendCommand(command);
            command = scan.nextLine();
        }

        scan.close();
        tester.closeControlSocket();
        System.out.println("Exit Program...");
    }

}