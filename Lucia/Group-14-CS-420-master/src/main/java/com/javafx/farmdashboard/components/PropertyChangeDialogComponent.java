package com.javafx.farmdashboard.components;

import com.javafx.farmdashboard.Application;
import com.javafx.farmdashboard.Constants;
import com.javafx.farmdashboard.model.Item;
import com.javafx.farmdashboard.model.ItemI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PropertyChangeDialogComponent extends HBox {

    @FXML
    Label value1Label = new Label();
    @FXML
    TextField value1Field = new TextField();

    @FXML
    Label value2Label = new Label();
    @FXML
    TextField value2Field = new TextField();

    Dialog<ButtonType> dialog = new Dialog<>();

    Alert alert = new Alert(Alert.AlertType.ERROR, "The values are out of bounds or invalid");

    public PropertyChangeDialogComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("property-change-dialog-content.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize() {

        DialogPane dialogPane = this.dialog.getDialogPane();

        dialogPane.getChildren().add(this);

        dialogPane.setMinWidth(400);
        dialogPane.setMinHeight(120);

        dialogPane.getButtonTypes().add(ButtonType.OK);
        dialogPane.getButtonTypes().add(ButtonType.CANCEL);

    }

    public List<String> triggerDialog(String command, ItemI item) {
        this.initializeFields(command, item);

        this.dialog.setTitle(item.getName() + ": " + command);

        EventHandler<ActionEvent> validationHandler = event -> {
            //System.out.println(command);
            //System.out.println(validateFields(command, item));
            if (!validateFields(command, item)) {
                event.consume();
                this.alert.showAndWait();
            }
        };

        final Button btOk = (Button) this.dialog.getDialogPane().lookupButton(ButtonType.OK);
        btOk.addEventFilter(ActionEvent.ACTION, validationHandler);

        Optional<ButtonType> result = this.dialog.showAndWait();

        //System.out.println(result);

        btOk.removeEventFilter(ActionEvent.ACTION, validationHandler);

        if (result.isPresent() && result.get() == ButtonType.OK) {
            return List.of(value1Field.getText(), value2Field.getText());
        } else {
            return Collections.emptyList();
        }
    }

    public void initializeFields(String command, ItemI item) {

        if (Objects.equals(command, Constants.Command.RENAME.getName())) {
            setSecondFieldVisibility(false);

            value1Field.setText(item.getName());
            value1Label.setText("Name");
        } else if (Objects.equals(command, Constants.Command.CHANGE_LOCATION.getName())) {
            setSecondFieldVisibility(true);

            value1Field.setText(String.valueOf(item.getPosX()));
            value1Label.setText("X");

            value2Field.setText(String.valueOf(item.getPosY()));
            value2Label.setText("Y");
        } else if (Objects.equals(command, Constants.Command.CHANGE_DIMENSIONS.getName())) {
            setSecondFieldVisibility(true);

            value1Field.setText(String.valueOf(item.getLength()));
            value1Label.setText("Length");

            value2Field.setText(String.valueOf(item.getWidth()));
            value2Label.setText("Width");
        } else if (Objects.equals(command, Constants.Command.CHANGE_PRICE.getName())) {
            setSecondFieldVisibility(false);

            value1Field.setText(String.valueOf(item.getPrice()));
            value1Label.setText("Price");
        } else if (Objects.equals(command, Constants.Command.CHANGE_MARKET_VALUE.getName())) {
            setSecondFieldVisibility(false);

            value1Field.setText(String.valueOf(item.getMarketValue()));
            value1Label.setText("Market Value");
        }

        requestFirstFieldFocus();
    }

    public boolean validateFields(String command, ItemI item) {
        try {
            if (Objects.equals(command, Constants.Command.CHANGE_LOCATION.getName())) {
                double value1 = Double.parseDouble(value1Field.getText());
                double value2 = Double.parseDouble(value2Field.getText());
                ItemI itemParent = item.getParent();
                return (value1 >= itemParent.getPosX() && value1 <= itemParent.getPosX() - item.getWidth() + itemParent.getWidth()) && (value2 >= itemParent.getPosY() && value2 <= itemParent.getPosY() - item.getLength() + itemParent.getLength());
            } else if (Objects.equals(command, Constants.Command.CHANGE_DIMENSIONS.getName())) {
                double value1 = Double.parseDouble(value1Field.getText());
                double value2 = Double.parseDouble(value2Field.getText());
                ItemI itemParent = item.getParent();
                return (item.getPosX() + value2 <= itemParent.getPosX() + itemParent.getWidth()) && (item.getPosY() + value1 <= itemParent.getPosY() + itemParent.getLength());
            } else if (Objects.equals(command, Constants.Command.RENAME.getName())) {
                return !value1Field.getText().isBlank();
            } else if (Objects.equals(command, Constants.Command.CHANGE_PRICE.getName())) {
                Double.parseDouble(value1Field.getText());
                return true;
            } else if (Objects.equals(command, Constants.Command.CHANGE_MARKET_VALUE.getName())) {
                Double.parseDouble(value1Field.getText());
                return true;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void setSecondFieldVisibility(boolean visible) {
        value2Field.setVisible(visible);
        value2Label.setVisible(visible);
    }

    public void requestFirstFieldFocus() {
        value1Field.requestFocus();
    }
}
