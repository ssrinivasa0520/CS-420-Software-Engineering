package com.javafx.farmdashboard.helpers;

import com.javafx.farmdashboard.Constants;
import com.javafx.farmdashboard.model.Item;
import com.javafx.farmdashboard.model.ItemI;
import com.javafx.farmdashboard.model.ItemLeaf;

import java.util.ArrayList;

public class ItemCreator {

    //public static int count = 0;
    public static ItemI createFarm() {
        ItemI item = createItem(Constants.FARM_X, Constants.FARM_Y, Constants.ItemType.FARM, true);
        item.setName("Farm");
        item.setLength(Constants.FARM_LENGTH);
        item.setWidth(Constants.FARM_WIDTH);
        return item;
    }

    public static ItemI createCommandCenter() {
        ItemI item = createItem(Constants.FARM_X + 20, Constants.FARM_Y + 20, Constants.ItemType.COMMAND_CENTER, true);
        item.setName("Command Center");
        item.setLength(150.0);
        item.setWidth(150.0);
        return item;
    }

    public static ItemI createDrone(double posX, double posY) {
        ItemI item = createItem(posX, posY, Constants.ItemType.DRONE, false);
        item.setName("Drone");
        item.setLength(Constants.DRONE_LENGTH);
        item.setWidth(Constants.DRONE_WIDTH);
        return item;

    }

    public static ItemI createItem() {
        return createItem(Constants.FARM_WIDTH / 2, Constants.FARM_LENGTH / 2);
    }

    public static ItemI createItem(double posX, double posY) {
        return createItem(posX, posY, Constants.ItemType.DEFAULT, false);
    }

    public static ItemI createItem(double posX, double posY, Constants.ItemType type, boolean isContainer) {
        return Item.builder()
                .name(isContainer ? "New Item Container" : "New Item")
                .price(0.0)
                .marketValue(0.0)
                .posX(posX)
                .posY(posY)
                .length(isContainer ? Constants.CONTAINER_LENGTH : 10.0)
                .width(isContainer ? Constants.CONTAINER_WIDTH : 10.0)
                .type(type)
                .isContainer(isContainer)
                .children(new ArrayList<>())
                .build();
    }

    public static ItemI createItemContainer(double posX, double posY) {
        return createItem(posX, posY, Constants.ItemType.DEFAULT, true);
    }
}
