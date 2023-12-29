package com.javafx.farmdashboard.components;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class DroneActionComponent {

    private final AnchorPane droneActionPane;

    private final RadioButton visitItemRadioButton;

    private final RadioButton scanFarmRadioButton;

    public DroneActionComponent(AnchorPane droneActionPane, RadioButton visitItemRadioButton, RadioButton scanFarmRadioButton) {
        this.droneActionPane = droneActionPane;
        this.visitItemRadioButton = visitItemRadioButton;
        this.scanFarmRadioButton = scanFarmRadioButton;
        final ToggleGroup group = new ToggleGroup();
        visitItemRadioButton.setToggleGroup(group);
        scanFarmRadioButton.setToggleGroup(group);
        scanFarmRadioButton.setSelected(true);
    }

    public void setDisable(boolean disabled) {
        droneActionPane.setDisable(disabled);
    }

}
