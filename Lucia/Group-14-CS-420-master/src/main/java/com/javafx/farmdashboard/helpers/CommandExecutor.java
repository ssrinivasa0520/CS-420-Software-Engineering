package com.javafx.farmdashboard.helpers;

import com.javafx.farmdashboard.Constants;
import com.javafx.farmdashboard.components.*;
import com.javafx.farmdashboard.model.ItemI;
import javafx.scene.control.TreeItem;

import java.util.List;
import java.util.Objects;

import static com.javafx.farmdashboard.Constants.*;

public class CommandExecutor {

    private final ItemViewComponent itemViewComponent;

    private final PropertyTableComponent propertyTableComponent;

    private final FarmVisualizationComponent farmVisualizationComponent;

    private final PropertyChangeDialogComponent propertyChangeDialog;

    private final DroneActionComponent droneActionComponent;

    private final PriceLabelComponent priceLabelComponent;
    public CommandExecutor(ItemViewComponent itemViewComponent, PropertyTableComponent propertyTableComponent, FarmVisualizationComponent farmVisualizationComponent, PropertyChangeDialogComponent propertyChangeDialog, DroneActionComponent droneActionComponent, PriceLabelComponent priceLabelComponent) {
        this.itemViewComponent = itemViewComponent;
        this.propertyTableComponent = propertyTableComponent;
        this.farmVisualizationComponent = farmVisualizationComponent;
        this.propertyChangeDialog = propertyChangeDialog;
        this.droneActionComponent = droneActionComponent;
        this.priceLabelComponent = priceLabelComponent;
    }

    public void executeCommand(String command) {

        if(Objects.equals(command, Constants.Command.ADD_ITEM.getName())) {
            addItem(Constants.ItemType.DEFAULT, false);
        } else if(Objects.equals(command, Constants.Command.ADD_ITEM_CONTAINER.getName())) {
            addItem(Constants.ItemType.DEFAULT,true);
        } else if(Objects.equals(command, Constants.Command.ADD_COMMAND_CENTER.getName())) {
            addItem(Constants.ItemType.COMMAND_CENTER,true);
        } else if(Objects.equals(command, Constants.Command.ADD_DRONE.getName())) {
            addItem(Constants.ItemType.DRONE,false);
        } else if(Objects.equals(command, Constants.Command.DELETE.getName())) {
            deleteItem();
        } else if(Objects.equals(command, Constants.Command.RENAME.getName())) {
            changeItemProperty(command);
        } else if(Objects.equals(command, Constants.Command.CHANGE_LOCATION.getName())) {
            changeItemProperty(command);
        } else if(Objects.equals(command, Constants.Command.CHANGE_DIMENSIONS.getName())) {
            changeItemProperty(command);
        } else if(Objects.equals(command, Constants.Command.CHANGE_PRICE.getName())) {
            changeItemProperty(command);
        } else if(Objects.equals(command, Command.CHANGE_MARKET_VALUE.getName())) {
            changeItemProperty(command);
        }
    }

    public ItemI getNewItem(ItemI parentItem, Constants.ItemType type, boolean isContainer) {
        ItemI newItem;

        double parentPosX = parentItem.getPosX();
        double parentPosY = parentItem.getPosY();

        double parentLength = parentItem.getLength();
        double parentWidth = parentItem.getWidth();

        if(type.equals(Constants.ItemType.DRONE)) {
            newItem = ItemCreator.createDrone(parentPosX + parentWidth / 2 - DRONE_LENGTH / 2, parentPosY + parentLength / 2 - DRONE_WIDTH / 2);
        } else if(type.equals(Constants.ItemType.COMMAND_CENTER)) {
            newItem = ItemCreator.createCommandCenter();
        } else {
            newItem = !isContainer ? ItemCreator.createItem(parentPosX + 10, parentPosY + 10, type, false) : ItemCreator.createItemContainer(parentPosX + parentWidth / 2 - CONTAINER_WIDTH / 2, parentPosY + parentLength / 2 - CONTAINER_LENGTH / 2);
        }
        return newItem;
    }

    public void addItem(Constants.ItemType type, boolean isContainer) {
        TreeItem<ItemI> selectedTreeItem = itemViewComponent.getSelectedTreeItem();
        ItemI selectedItem = selectedTreeItem.getValue();

        ItemI newItem = getNewItem(selectedItem, type, isContainer);

        newItem.setParent(selectedItem);
        selectedItem.getChildren().add(newItem);
        if(newItem.isDrone() || newItem.isCommandCenter()) {
            selectedTreeItem.getChildren().add(0, new TreeItem<>(newItem));
        } else {
            selectedTreeItem.getChildren().add(new TreeItem<>(newItem));
        }
        selectedTreeItem.setExpanded(true);

        postAddActions(newItem);

        refreshFarmVisualization();
    }

    public void postAddActions(ItemI item) {
        if(item.isDrone()) {
            DroneComponent.droneItem = item;
            droneActionComponent.setDisable(false);
        } else if(item.isCommandCenter()) {
            CommandCenterComponent.commandCenter = item;
        }
    }

    public void deleteItem() {
        TreeItem<ItemI> selectedTreeItem = itemViewComponent.getSelectedTreeItem();
        ItemI selectedItem = selectedTreeItem.getValue();

        selectedTreeItem.getParent().getChildren().remove(selectedTreeItem);
        selectedItem.getParent().getChildren().remove(selectedItem);

        postDeleteActions(selectedItem);

        refreshFarmVisualization();
    }

    public void postDeleteActions(ItemI item) {
        if(item.isDrone()) {
            DroneComponent.droneItem = null;
            droneActionComponent.setDisable(true);
        } else if(item.isCommandCenter()) {
            CommandCenterComponent.commandCenter = null;
            DroneComponent.droneItem = null;
            droneActionComponent.setDisable(true);
        }
    }

    public void changeItemProperty(String command) {
        ItemI selectedItem = itemViewComponent.getSelectedItem();
        List<String> changedValues = propertyChangeDialog.triggerDialog(command, selectedItem);

        //System.out.println(changedValues);

        if(!changedValues.isEmpty()) {
            if(Objects.equals(command, Constants.Command.RENAME.getName())) {
                selectedItem.setName(String.valueOf(changedValues.get(0)));
            } else if(Objects.equals(command, Constants.Command.CHANGE_PRICE.getName())) {
                selectedItem.setPrice(Double.valueOf(changedValues.get(0)));
            } else if(Objects.equals(command, Command.CHANGE_MARKET_VALUE.getName())) {
                selectedItem.setMarketValue(Double.valueOf(changedValues.get(0)));
            } else if(Objects.equals(command, Constants.Command.CHANGE_LOCATION.getName())) {
                changeLocation(selectedItem, Double.parseDouble(changedValues.get(0)), Double.parseDouble(changedValues.get(1)));
            } else if(Objects.equals(command, Constants.Command.CHANGE_DIMENSIONS.getName())) {
                selectedItem.setLength(Double.valueOf(changedValues.get(0)));
                selectedItem.setWidth(Double.valueOf(changedValues.get(1)));
            }

            refreshTreeView();
            refreshPropertyTable();
            refreshLabels();
            refreshFarmVisualization();
        }
    }

    public void changeLocation(ItemI item, double posX, double posY) {
        double offsetX = posX - item.getPosX();
        double offsetY = posY - item.getPosY();

        item.setPosX(posX);
        item.setPosY(posY);

        item.getChildren().forEach((child) -> {
            offsetLocation(child, offsetX, offsetY);
        });
    }

    public void offsetLocation(ItemI item, double offsetX, double offsetY) {
        item.setPosX(item.getPosX() + offsetX);
        item.setPosY(item.getPosY() + offsetY);

        item.getChildren().forEach((child) -> {
            offsetLocation(child, offsetX, offsetY);
        });
    }

    public void refreshTreeView() {
        itemViewComponent.refresh();
    }

    public void refreshPropertyTable() {
        ItemI selectedItem = itemViewComponent.getSelectedItem();
        propertyTableComponent.displayProperties(selectedItem);
    }

    public void refreshLabels() {
        ItemI selectedItem = itemViewComponent.getSelectedItem();
        priceLabelComponent.refreshPurchasePrice(selectedItem);
        priceLabelComponent.refreshMarketValue(selectedItem);
    }

    public void refreshFarmVisualization() {
        farmVisualizationComponent.render(itemViewComponent.getRootItem());
    }

}
