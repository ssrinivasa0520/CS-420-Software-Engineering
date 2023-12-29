package items;

import java.io.IOException;

interface FlightControls { //Interface for flight commands
    String test() throws IOException;
    void endFlight();
    void hover(int x) throws IOException, InterruptedException;
    void takeOff() throws  IOException;
    void landDrone() throws  IOException;
    void moveForward(int x) throws IOException;
    void moveToXYZ(int x, int y, int z, int speed) throws IOException;
    void rotateCCW(int x) throws IOException;
    void rotateCW(int x) throws IOException;
    default void testBattery() throws IOException{
    }
}
