package com.javafx.farmdashboard.controller;

import com.javafx.farmdashboard.Constants;
import com.javafx.farmdashboard.tellolib.command.BasicTelloCommand;
import com.javafx.farmdashboard.tellolib.communication.TelloCommunication;
import com.javafx.farmdashboard.tellolib.communication.TelloConnection;
import com.javafx.farmdashboard.tellolib.control.TelloControl;
import com.javafx.farmdashboard.tellolib.drone.TelloDrone;
import lombok.Data;

import java.util.logging.Level;
import java.util.logging.Logger;

@Data
public class TelloDroneController {

    public final int MIN_X = -500 + Double.valueOf(Constants.FARM_X).intValue(), MAX_X = MIN_X + Double.valueOf(Constants.FARM_WIDTH).intValue(), MIN_Y = -500 + Double.valueOf(Constants.FARM_Y).intValue(), MAX_Y = MIN_Y + Double.valueOf(Constants.FARM_LENGTH).intValue(), MIN_DIST = 0, MAX_SPEED = 100, MIN_SPEED = 10, MAX_DEGREES = 360, MIN_DEGREES = 1;
    public final int MAX_DIST = MAX_X;

    private final TelloControl telloControl = TelloControl.getInstance();

    private final TelloCommunication telloCommunication = TelloCommunication.getInstance();

    private final TelloDrone drone = TelloDrone.getInstance();

    private final Logger logger = Logger.getGlobal();

    public TelloDroneController() {
        telloControl.setLogLevel(Level.FINE);
    }

    public void connect() {

        logger.info("Start Tello Drone");

        telloControl.connect();

        telloControl.enterCommandMode();

        telloControl.startStatusMonitor();

    }

    public void takeOff() throws Exception {
        telloControl.takeOff();
    }

    public void land() throws Exception {
        if (telloControl.getConnection() == TelloConnection.CONNECTED && drone.isFlying()) {
            telloControl.land();
        }
    }

    public void disconnect() {
        telloControl.disconnect();
        logger.info("Stop Tello Drone");
    }

    public void forward(int distance) {
        telloControl.forward(distance);
    }

    public void rotateCW(int degrees) {
        telloControl.rotateRight(degrees);
    }

    public void rotateCCW(int degrees) {
        telloControl.rotateLeft(degrees);
    }

    public void gotoXY(int x, int y, int speed) {
        this.telloCommunication.executeCommand(new BasicTelloCommand(String.format("go %1$d %2$d %3$d %4$d", x, y, 0, speed)));
    }

    /*public void gotoXY(int x, int y, int speed) throws IOException {
        int z = 0;
        double slope = (double)y/x;
        if (speed > MAX_SPEED) speed = MAX_SPEED;
        else if (speed < MIN_SPEED) speed = MIN_SPEED;
        if (x <= MAX_X && x >= MIN_X && y <= MAX_Y && y >= MIN_Y) {
            this.telloCommunication.executeCommand(new BasicTelloCommand(String.format("go %1$d %2$d %3$d %4$d", x, -y, z, speed)));
        }
        else if ((x > MAX_X && y <= MAX_Y && y >= MIN_Y) || ((x > MAX_X || x < MIN_X) && (y > MAX_Y || y < MIN_Y) && (Math.abs(x) > Math.abs(y)) && (x > MAX_X))) {
            int partialY = (int) Math.round(slope * MAX_X);
            this.telloCommunication.executeCommand(new BasicTelloCommand(String.format("go %1$d %2$d %3$d %4$d", MAX_X, -partialY, z, speed)));
            gotoXY(x + MIN_X, y - partialY, speed);
        }
        else if (x < MIN_X && y <= MAX_Y && y >= MIN_Y || (x > MAX_X || x < MIN_X) && Math.abs(x) > Math.abs(y) && x < MIN_X) {
            int partialY = (int) Math.round(slope * MIN_X);
            this.telloCommunication.executeCommand(new BasicTelloCommand(String.format("go %1$d %2$d %3$d %4$d", MIN_X, partialY, z, speed)));
            gotoXY(x + MAX_X, y - partialY, speed);
        }
        else if ((y > MAX_X && x <= MAX_X && x >= MIN_X) || (((x > MAX_X || x < MIN_X) && (y > MAX_X || y < MIN_X)) && (Math.abs(y) > Math.abs(x)) && (y > MAX_X))) {
            int partialX = (int) Math.round(MAX_X /slope);
            this.telloCommunication.executeCommand(new BasicTelloCommand(String.format("go %1$d %2$d %3$d %4$d", partialX, MIN_Y, z, speed)));
            gotoXY(x - partialX, y + MIN_X, speed);
        }
        else if ((y < MIN_X && x <= MAX_X && x >= MIN_X) || (((x > MAX_X || x < MIN_X) && (y > MAX_X || y < MIN_X)) && (Math.abs(y) > Math.abs(x)) && (y < MAX_X))) {
            int partialX = (int) Math.round(MIN_X /slope);
            this.telloCommunication.executeCommand(new BasicTelloCommand(String.format("go %1$d %2$d %3$d %4$d", partialX, MAX_Y, z, speed)));
            gotoXY(x - partialX, y + MAX_X, speed);
        }
        else {
            if (x > MAX_X && y < MIN_X) {
                this.telloCommunication.executeCommand(new BasicTelloCommand(String.format("go %1$d %2$d %3$d %4$d", MAX_X, MAX_Y, z, speed)));
                gotoXY(x + MIN_X, y + MAX_X, speed);
            }
            else if (x < MIN_X && y > MAX_X) {
                this.telloCommunication.executeCommand(new BasicTelloCommand(String.format("go %1$d %2$d %3$d %4$d", MIN_X, MIN_Y, z, speed)));
                gotoXY(x + MAX_X, y + MIN_X, speed);
            }
            else if (x > MAX_X && x == y) {
                this.telloCommunication.executeCommand(new BasicTelloCommand(String.format("go %1$d %2$d %3$d %4$d", MAX_X, MIN_Y, z, speed)));
                gotoXY(x + MIN_X, y + MIN_X, speed);
            }
            else {
                this.telloCommunication.executeCommand(new BasicTelloCommand(String.format("go %1$d %2$d %3$d %4$d", MIN_X, MAX_Y, z, speed)));
                gotoXY(x + MAX_X, y + MAX_X, speed);
            }
        }
    }*/

}
