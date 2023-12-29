package items;

import java.io.IOException;

interface FlightControls {
    public void activateSDK() throws IOException;
    public void end() throws IOException, InterruptedException;
    public void takeoff() throws IOException;
    public void land() throws IOException;
    public void increaseAltitude(int up) throws IOException;
    public void decreaseAltitude(int down) throws IOException;
    public void flyForward(int front) throws IOException;
    public void flyLeft(int left) throws IOException;
    public void flyRight(int right) throws IOException;
    public void turnCW(int degrees) throws IOException;
    void flyBackward(int back) throws IOException;
    void hoverInPlace(int seconds) throws InterruptedException, IOException;
    public int getHeight() throws IOException;
    public int getAttitudePitch() throws IOException;
    public int getAttitudeRoll() throws IOException;
    public int getAttitudeYaw() throws IOException;
    public double getAccelerationX() throws IOException;
    public double getAccelerationY() throws IOException;
    public double getAccelerationZ() throws IOException;

}
