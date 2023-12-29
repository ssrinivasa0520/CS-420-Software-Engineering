package com.javafx.farmdashboard;

import lombok.Getter;

public class Constants {

    public static final double FARM_X = 40;
    public static final double FARM_Y = 40;
    public static final double FARM_LENGTH = 600.0;
    public static final double FARM_WIDTH = 800.0;
    public static final double CONTAINER_LENGTH = 40.0;
    public static final double CONTAINER_WIDTH = 40.0;
    public static final double DRONE_LENGTH = 75;
    public static final double DRONE_WIDTH = 75;

    public enum ItemType {
        DEFAULT, FARM, DRONE, COMMAND_CENTER,
    }

    @Getter
    public enum Command {

        RENAME("Rename"),
        CHANGE_LOCATION("Change Location"),
        CHANGE_PRICE("Change Price"),
        CHANGE_MARKET_VALUE("Change Market Value"),
        CHANGE_DIMENSIONS("Change Dimensions"),
        ADD_DRONE("Add Drone"),
        ADD_COMMAND_CENTER("Add Command Center"),
        ADD_ITEM("Add Item"),
        ADD_ITEM_CONTAINER("Add Item Container"),
        DELETE("Delete");

        public final String name;

        Command(String name) {
            this.name = name;
        }

    }
}
