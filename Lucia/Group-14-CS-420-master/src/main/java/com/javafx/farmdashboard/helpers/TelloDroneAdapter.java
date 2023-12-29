package com.javafx.farmdashboard.helpers;

import com.javafx.farmdashboard.model.Coordinates;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface TelloDroneAdapter {

    public void scanFarm(Coordinates finalCoordinates, EventHandler<ActionEvent> onStart, EventHandler<ActionEvent> onFinish) throws Exception;

    public void visitItem(Coordinates itemCoordinates, EventHandler<ActionEvent> onStart, EventHandler<ActionEvent> onFinish) throws Exception;

}
