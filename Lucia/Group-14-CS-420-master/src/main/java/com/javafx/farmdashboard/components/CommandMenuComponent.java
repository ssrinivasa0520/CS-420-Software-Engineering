package com.javafx.farmdashboard.components;

import com.javafx.farmdashboard.Constants;
import com.javafx.farmdashboard.model.Item;
import com.javafx.farmdashboard.model.ItemI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

public class CommandMenuComponent {

    private final ContextMenu commandMenu;

    private final MenuItem RENAME = new MenuItem(Constants.Command.RENAME.getName());
    private final MenuItem CHANGE_LOCATION = new MenuItem(Constants.Command.CHANGE_LOCATION.getName());
    private final MenuItem CHANGE_PRICE = new MenuItem(Constants.Command.CHANGE_PRICE.getName());
    private final MenuItem CHANGE_MARKET_VALUE = new MenuItem(Constants.Command.CHANGE_MARKET_VALUE.getName());
    private final MenuItem CHANGE_DIMENSIONS = new MenuItem(Constants.Command.CHANGE_DIMENSIONS.getName());
    private final MenuItem DELETE = new MenuItem(Constants.Command.DELETE.getName());
    private final MenuItem ADD_ITEM = new MenuItem(Constants.Command.ADD_ITEM.getName());
    private final MenuItem ADD_ITEM_CONTAINER = new MenuItem(Constants.Command.ADD_ITEM_CONTAINER.getName());
    private final MenuItem ADD_COMMAND_CENTER = new MenuItem(Constants.Command.ADD_COMMAND_CENTER.getName());
    private final MenuItem ADD_DRONE = new MenuItem(Constants.Command.ADD_DRONE.getName());

    private final List<MenuItem> ALL_COMMANDS = List.of(
            RENAME, CHANGE_LOCATION, CHANGE_PRICE, CHANGE_DIMENSIONS, CHANGE_MARKET_VALUE, DELETE, ADD_ITEM, ADD_ITEM_CONTAINER, ADD_COMMAND_CENTER, ADD_DRONE
    );

    private final List<MenuItem> ITEM_COMMANDS = List.of(
            RENAME, CHANGE_LOCATION, CHANGE_PRICE, CHANGE_MARKET_VALUE, CHANGE_DIMENSIONS, DELETE
    );

    private final List<MenuItem> ITEM_CONTAINER_COMMANDS = List.of(
            ADD_ITEM, ADD_ITEM_CONTAINER, RENAME, CHANGE_LOCATION, CHANGE_PRICE, CHANGE_DIMENSIONS, DELETE
    );

    private final List<MenuItem> FARM_COMMANDS = List.of(
            ADD_COMMAND_CENTER, ADD_ITEM, ADD_ITEM_CONTAINER, RENAME, CHANGE_PRICE
    );

    private final List<MenuItem> FARM_COMMANDS_ALT = List.of(
            ADD_ITEM, ADD_ITEM_CONTAINER, RENAME, CHANGE_PRICE
    );

    private final List<MenuItem> COMMAND_CENTER_COMMANDS = List.of(
            ADD_DRONE, ADD_ITEM, ADD_ITEM_CONTAINER, RENAME, CHANGE_LOCATION, CHANGE_PRICE, CHANGE_DIMENSIONS, DELETE
    );

    public CommandMenuComponent(ContextMenu contextMenu, EventHandler<ActionEvent> onCommandClicked) {
        commandMenu = contextMenu;

        ALL_COMMANDS.forEach(item -> item.setOnAction(onCommandClicked));

        commandMenu.getItems().addAll(ITEM_COMMANDS);
    }

    public void setView(ItemI item) {
        commandMenu.getItems().clear();

        Constants.ItemType type = item.getType();
        if(item.isDefault()) {
            if(item.isContainer()) commandMenu.getItems().addAll(ITEM_CONTAINER_COMMANDS);
            else commandMenu.getItems().addAll(ITEM_COMMANDS);
        } else if (item.isFarm()) {
            if(!CommandCenterComponent.isCommandCenterPresent()) {
                commandMenu.getItems().addAll(FARM_COMMANDS);
            } else {
                commandMenu.getItems().addAll(FARM_COMMANDS_ALT);
            }
        } else if (item.isCommandCenter()) {
            if(!DroneComponent.isDronePresent()) {
                commandMenu.getItems().addAll(COMMAND_CENTER_COMMANDS);
            } else {
                commandMenu.getItems().addAll(ITEM_CONTAINER_COMMANDS);
            }
        } else {
            commandMenu.getItems().addAll(ITEM_COMMANDS);
        }
    }

}
