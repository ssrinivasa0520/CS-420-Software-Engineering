package items;

import java.io.IOException;

interface FlightControls {
    public String test() throws IOException;
    public void activateSDK() throws IOException;
    public void endFlight();
    public void hover(int x) throws IOException, InterruptedException;
    public void takeOff() throws  IOException;
    public void landDrone() throws  IOException;
    public void moveBack(int x) throws IOException;
    public void moveForward(int x) throws IOException;
    public void moveLeft(int x) throws IOException;
    public void moveRight(int x) throws IOException;
    public void moveToXYZ(int x, int y, int z, int speed) throws IOException;
    public void rotateCCW(int x) throws IOException;
    public void rotateCW(int x) throws IOException;
    public default int testBattery() throws IOException{
        return 0;
    };
}
