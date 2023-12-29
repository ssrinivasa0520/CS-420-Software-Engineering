package com.javafx.farmdashboard.controller;

import com.javafx.farmdashboard.Application;
import com.javafx.farmdashboard.components.*;
import com.javafx.farmdashboard.helpers.CommandExecutor;
import com.javafx.farmdashboard.model.ItemI;
import com.javafx.farmdashboard.model.Property;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class DashboardController {

    private final ImageView drone = new ImageView(new Image(Objects.requireNonNull(Application.class.getResourceAsStream("images/drone.png"))));

    private CommandExecutor commandExecutor;

    @FXML
    private ContextMenu commandMenu;

    @FXML
    private TreeView<ItemI> itemView;

    @FXML
    private TableView<Property> propertyTableView;

    @FXML
    private TableColumn<Property, StringProperty> propertyNameColumn;

    @FXML
    private TableColumn<Property, StringProperty> propertyValueColumn;

    @FXML
    private AnchorPane farmVisualization;

    @FXML
    private AnchorPane droneActionsPane;

    @FXML
    private RadioButton visitItemRadioButton;

    @FXML
    private RadioButton scanFarmRadioButton;

    @FXML
    private Label purchasePriceLabel;

    @FXML
    private Label currentMarketValueLabel;

    private CommandMenuComponent commandMenuComponent;

    private ItemViewComponent itemViewComponent;

    private FarmVisualizationComponent farmVisualizationComponent;

    private PropertyTableComponent propertyTableComponent;

    private DroneComponent droneComponent;

    private final PropertyChangeDialogComponent propertyChangeDialog = new PropertyChangeDialogComponent();

    private DroneActionComponent droneActionComponent;

    private PriceLabelComponent priceLabelComponent;

    @FXML
    public void initialize() {

        drone.setCache(true);

        itemViewComponent = new ItemViewComponent(itemView);
        propertyTableComponent = new PropertyTableComponent(propertyTableView, propertyNameColumn, propertyValueColumn);

        farmVisualizationComponent = new FarmVisualizationComponent(farmVisualization, drone);
        farmVisualizationComponent.render(itemViewComponent.getRootItem());

        propertyTableComponent.displayProperties(itemViewComponent.getRootItem());

        droneActionComponent = new DroneActionComponent(droneActionsPane, visitItemRadioButton, scanFarmRadioButton);

        droneComponent = new DroneComponent(drone, itemViewComponent, droneActionComponent);

        priceLabelComponent = new PriceLabelComponent(purchasePriceLabel, currentMarketValueLabel, itemViewComponent);

        commandExecutor = new CommandExecutor(itemViewComponent, propertyTableComponent, farmVisualizationComponent, propertyChangeDialog, droneActionComponent, priceLabelComponent);

        commandMenuComponent = new CommandMenuComponent(commandMenu, (event) -> {
            String command = ((MenuItem)event.getTarget()).getText();
            commandExecutor.executeCommand(command);
        });

        visitItemRadioButton.setDisable(true);
        droneActionComponent.setDisable(true);
    }

    @FXML
    public void handleCommandMenuAction(EventHandler<ActionEvent> value) {
    }

    @FXML
    public void handleItemViewMousePress(MouseEvent event) {
        ItemI selectedItem = itemViewComponent.getSelectedItem();

        propertyTableComponent.displayProperties(selectedItem);

        commandMenuComponent.setView(selectedItem);

        priceLabelComponent.refreshPurchasePrice(selectedItem);

        priceLabelComponent.refreshMarketValue(selectedItem);

        visitItemRadioButton.setDisable(!selectedItem.isDefault());
    }

    @FXML
    public void handleStartSimulationButtonClicked(MouseEvent event) {
        if(scanFarmRadioButton.isSelected()) {
           droneComponent.simulateScanFarm();
        }
        if (visitItemRadioButton.isSelected() && !visitItemRadioButton.isDisabled()) {
            droneComponent.simulateVisitItem();
        }
    }

    @FXML
    public void handleLaunchDroneButtonClicked(MouseEvent event) {
        if(scanFarmRadioButton.isSelected()) {
            droneComponent.physicallyScanFarm();
        }
        if (visitItemRadioButton.isSelected() && !visitItemRadioButton.isDisabled()) {
            droneComponent.physicallyVisitItem();
        }
    }
}
