package com.javafx.farmdashboard.components;

import com.javafx.farmdashboard.controller.DroneController;
import com.javafx.farmdashboard.helpers.DroneAnimator;
import com.javafx.farmdashboard.helpers.TelloDroneAdapter;
import com.javafx.farmdashboard.model.Coordinates;
import com.javafx.farmdashboard.model.ItemI;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class DroneComponent {

    private ImageView drone;

    public static ItemI droneItem;

    private ItemViewComponent itemViewComponent;

    private DroneActionComponent droneActionComponent;

    private TelloDroneAdapter droneAnimator;

    private TelloDroneAdapter droneController;

    public DroneComponent(ImageView imageView, ItemViewComponent itemViewComponent, DroneActionComponent droneActionComponent) {
        drone = imageView;
        droneAnimator = new DroneAnimator(drone);
        droneController = new DroneController();
        this.itemViewComponent = itemViewComponent;
        this.droneActionComponent = droneActionComponent;
    }

    public static boolean isDronePresent() {
        return Objects.nonNull(droneItem);
    }

    public void simulateScanFarm() {
        if(isDronePresent()) {
            double offsetX = droneItem.getWidth() / 2;
            double offsetY = droneItem.getLength() / 2;
            try {
                droneAnimator.scanFarm(new Coordinates(droneItem.getPosX(), droneItem.getPosY(), offsetX, offsetY), (status) -> {
                    itemViewComponent.setDisable(true);
                    droneActionComponent.setDisable(true);
                }, (status) -> {
                    itemViewComponent.setDisable(false);
                    droneActionComponent.setDisable(false);
                });
            } catch (Exception e){
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void physicallyScanFarm() {
        if(isDronePresent()) {
            double offsetX = -500;
            double offsetY = -500;
            try {
                droneController.scanFarm(new Coordinates(droneItem.getPosX(), droneItem.getPosY(), offsetX, offsetY), (status) -> {
                    itemViewComponent.setDisable(true);
                    droneActionComponent.setDisable(true);
                }, (status) -> {
                    itemViewComponent.setDisable(false);
                    droneActionComponent.setDisable(false);
                });
            } catch (Exception e){
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void simulateVisitItem() {
        ItemI selectedItem = itemViewComponent.getSelectedItem();
        if(isDronePresent()) {
            double offsetX = droneItem.getWidth() / 2;
            double offsetY = droneItem.getLength() / 2;
            try {
                droneAnimator.visitItem(new Coordinates(selectedItem.getPosX(), selectedItem.getPosY(), offsetX, offsetY), (status) -> {
                    itemViewComponent.setDisable(true);
                    droneActionComponent.setDisable(true);
                }, (status) -> {
                    itemViewComponent.setDisable(false);
                    droneActionComponent.setDisable(false);
                });
            } catch (Exception e){
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void physicallyVisitItem() {
        ItemI selectedItem = itemViewComponent.getSelectedItem();
        if(isDronePresent()) {
            double offsetX = -500;
            double offsetY = -500;
            try {
                droneController.visitItem(new Coordinates(selectedItem.getPosX(), selectedItem.getPosY(), offsetX, offsetY), (status) -> {
                    itemViewComponent.setDisable(true);
                    droneActionComponent.setDisable(true);
                }, (status) -> {
                    itemViewComponent.setDisable(false);
                    droneActionComponent.setDisable(false);
                });
            } catch (Exception e){
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        }
    }
}
