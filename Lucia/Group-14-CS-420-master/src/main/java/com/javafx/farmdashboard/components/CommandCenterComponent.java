package com.javafx.farmdashboard.components;

import com.javafx.farmdashboard.model.ItemI;

import java.util.Objects;

public class CommandCenterComponent {
    public static ItemI commandCenter;

    public static boolean isCommandCenterPresent() {
        return Objects.nonNull(commandCenter);
    }
}
