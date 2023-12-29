package com.javafx.farmdashboard.controller;

import com.javafx.farmdashboard.components.DroneComponent;
import com.javafx.farmdashboard.helpers.TelloDroneAdapter;
import com.javafx.farmdashboard.model.Coordinates;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DroneController extends TelloDroneController implements TelloDroneAdapter {

    public int toInt(double value) {
        return Double.valueOf(value).intValue();
    }

    @Override
 /*   public void scanFarm(Coordinates finalCoordinates, EventHandler<ActionEvent> onStart, EventHandler<ActionEvent> onFinish) throws Exception {
        int initPosX = MIN_X;
        int initPosY = MIN_Y;
        int finalPosX = toInt(finalCoordinates.getXAfterOffset());
        int finalPosY = toInt(finalCoordinates.getYAfterOffset());
        //TelloControl telloControl = super.getTelloControl();
        int speed = 50;
        try {
            super.connect();
            onStart.handle(null);

            super.takeOff();
            super.gotoXY(initPosX, initPosY, speed);
            TimeUnit.SECONDS.sleep(10);

            for(int i = 25; i <= MAX_X; i += 25) {
                super.gotoXY(initPosX + i, MIN_Y, speed);
                TimeUnit.SECONDS.sleep(10);
                super.gotoXY(initPosX + i, MAX_Y, speed);
                TimeUnit.SECONDS.sleep(10);
                i += 25;
                super.gotoXY(initPosX + i, MAX_Y, speed);
                TimeUnit.SECONDS.sleep(10);
                super.gotoXY(initPosX + i, MIN_Y, speed);
                TimeUnit.SECONDS.sleep(10);
            }

            super.gotoXY(finalPosX, finalPosY, speed);
            TimeUnit.SECONDS.sleep(10);

            super.land();
            super.disconnect();

        } catch (Exception e) {
            throw e;
        } finally {
            onFinish.handle(null);
        }
    }*/

    public void scanFarm(Coordinates finalCoordinates, EventHandler<ActionEvent> onStart, EventHandler<ActionEvent> onFinish) throws Exception { //drone command to scan farm
        int dXPos = (int) (DroneComponent.droneItem.getPosX() / 15 * 30.48);
        int dYPos = (int) (DroneComponent.droneItem.getPosY() / 15 * 30.48);

        int speed = 50;

        try {

            connect();
            onStart.handle(null);

            takeOff();
            gotoXY(dYPos, dXPos, speed);

            for (int i = 0; i <= 7; i++) {
                forward((int) ((15 * 30.48) / 2));
                forward((int) ((15 * 30.48) / 2));
                rotateCCW(90);
                forward((int) (30.48 * 2));
                rotateCCW(90);
                forward((int) ((15 * 30.48) / 2));
                forward((int) ((15 * 30.48) / 2));
                rotateCW(90);
                forward((int) (30.48 * 2));
                rotateCW(90);
            }
            forward((int) ((15 * 30.48) / 2));
            forward((int) ((15 * 30.48) / 2));
            gotoXY(-(((int) ((600 / 15) * 30.48) - dYPos)), -(((int) ((800 / 15) * 30.48) - dXPos)), speed);

            land();
            disconnect();

        } catch (Exception e) {
            throw e;
        } finally {
            onFinish.handle(null);
        }
    }

    @Override
 /*   public void visitItem(Coordinates itemCoordinates, EventHandler<ActionEvent> onStart, EventHandler<ActionEvent> onFinish) throws Exception{
        int initPosX = toInt(DroneComponent.droneItem.getPosX() + itemCoordinates.getOffsetX());
        int initPosY = toInt(DroneComponent.droneItem.getPosY() + itemCoordinates.getOffsetY());
        int finalPosX = toInt(itemCoordinates.getXAfterOffset());
        int finalPosY = toInt(itemCoordinates.getYAfterOffset());

        int speed = 50;

        try {
            super.connect();
            onStart.handle(null);

            super.takeOff();
            super.gotoXY(initPosX, initPosY, speed);
            super.rotateCCW(360);


            TimeUnit.SECONDS.sleep(5);

            super.gotoXY(finalPosX, finalPosY, speed);

            super.land();
            super.disconnect();

        } catch (Exception e) {
            throw e;
        } finally {
            onFinish.handle(null);
        }

    }*/

    public void visitItem(Coordinates itemCoordinates, EventHandler<ActionEvent> onStart, EventHandler<ActionEvent> onFinish) throws Exception{
        int dXPos = (int) (DroneComponent.droneItem.getPosX() / 25 * 30.48);
        int dYPos = (int) (DroneComponent.droneItem.getPosY() / 25 * 30.48);
        int xPos = (int) (((itemCoordinates.getXAfterOffset() / 25) * 30.48) - dYPos);
        int yPos = (int) (((itemCoordinates.getYAfterOffset() / 25) * 30.48) - dXPos);

        int speed = 50;

        try {

            connect();
            onStart.handle(null);

            takeOff();
            gotoXY(xPos, yPos, speed);
            rotateCCW(360);

            TimeUnit.SECONDS.sleep(5);

            gotoXY(dXPos, dYPos, speed);

            land();
            disconnect();

        } catch (Exception e) {
            throw e;
        } finally {
            onFinish.handle(null);
        }
    }
}
