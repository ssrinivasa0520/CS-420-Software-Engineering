package items;

import java.io.IOException;

interface FlightControls {
    public String test() throws IOException;
    public int getBattery() throws IOException;
    public void activateSDK() throws IOException;
    public void endFlight() throws IOException, InterruptedException;
    public void takeOff() throws  IOException;
    public void landDrone() throws  IOException;
    public void moveBack(int x) throws IOException;
    public void moveForward(int x) throws IOException;
    public void moveLeft(int x) throws IOException;
    public void moveRight(int x) throws IOException;
}
