package com.example.designandimplementation;

import javax.swing.*;

import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.util.ArrayList;


import javafx.util.Duration;
import javafx.scene.text.Text;
import items.*;

public class Controller{
    private int droneCount = 0;
    private ArrayList<ItemInfo> farmItemList;
    @FXML
    private TreeView<ItemInfo> treeView;
    @FXML
    private ImageView drone;
    @FXML
    private AnchorPane farmVisualization;
    @FXML
    private TextField nameField;
    @FXML
    private TextField posXField;
    @FXML
    private TextField posYField;
    @FXML
    private TextField lengthField;
    @FXML
    private TextField widthField;
    @FXML
    private TextField valueField;
    @FXML
    private Button scanFarmButton;
    @FXML
    private Button visitButton;
    @FXML
    private AnchorPane attributesPane;
    @FXML
    private MenuItem addDrone;
    @FXML
    private MenuItem addItem;
    @FXML
    private MenuItem addContainer;
    @FXML
    private MenuItem delete;
    @FXML
    private MenuItem attributes;
    @FXML
    private ColorPicker fontColorID;
    @FXML
    private ColorPicker containerColorID;
    private double xPos;
    private double yPos;



    @FXML
    public void initialize() {
        Singleton.getInstance();
        farmItemList = new ArrayList<ItemInfo>();
        ItemLeaf farm = new ItemLeaf("Farm", 0, 0,0,0,0, Color.BLACK, Color.BLACK);
        TreeItem<ItemInfo> rootItem = new TreeItem<ItemInfo>(farm);
        rootItem.setExpanded(true);
        treeView.setRoot(rootItem);
        treeView.getSelectionModel().selectedItemProperty().addListener((ChangeListener)
                (observable, oldValue, newValue) -> {
                    ItemInfo selectedItem = getSelectedItem();
                    if (selectedItem == null) {
                        treeView.getSelectionModel().select(treeView.getRoot());
                        selectedItem = getSelectedItem();
                    };
                    nameField.setText(selectedItem.getName());
                    posXField.setText(String.valueOf(selectedItem.getPosX()));
                    posYField.setText(String.valueOf(selectedItem.getPosY()));
                    lengthField.setText(String.valueOf(selectedItem.getLength()));
                    widthField.setText(String.valueOf(selectedItem.getWidth()));
                    valueField.setText(String.valueOf(selectedItem.getValue()));
                    containerColorID.setValue(selectedItem.getContainerColor());
                    fontColorID.setValue(selectedItem.getFontColor());
                });
    }
    ItemInfo getSelectedItem(){
        TreeItem<ItemInfo> itemSelected = treeView.getSelectionModel().getSelectedItem();
        if (itemSelected == null){
            return null;
        }
        return itemSelected.getValue();
    }
    ItemInfo getSelectedItemParent(){
        TreeItem<ItemInfo> itemSelected = treeView.getSelectionModel().getSelectedItem();
        if (itemSelected == null){
            return null;
        }
        return itemSelected.getParent().getValue();
    }
    @FXML
    private void treeViewClick(MouseEvent event){
        if (event.isPrimaryButtonDown()){
            attributesPane.setVisible(false);
            visitButton.setDisable(true);
            scanFarmButton.setDisable(true);
            if (getSelectedItem() == null){
                return;
            }else if(getSelectedItemIndex() == 0){
                visitButton.setText("Visit " + getSelectedItem().getName());
            }else if (getSelectedItem().hasNext() && getSelectedItem().getCommandCenter() == 1){
                visitButton.setDisable(false);
                scanFarmButton.setDisable(false);
                visitButton.setText("Visit " + getSelectedItem().getName());
            }else if(getSelectedItem().getDrone() == 1){
                visitButton.setText("Visit " + getSelectedItem().getName());
            }else if (getSelectedItem().getChildren() != null){
                visitButton.setDisable(false);
                visitButton.setText("Visit " + getSelectedItem().getName());
            }else{
                visitButton.setDisable(false);
                visitButton.setText("Visit " + getSelectedItem().getName());
            }
        }
        if (event.isSecondaryButtonDown()){
            scanFarmButton.setDisable(true);
            visitButton.setDisable(true);
            addDrone.setDisable(true);
            addItem.setDisable(true);
            addContainer.setDisable(true);
            delete.setDisable(true);
            addDrone.setVisible(false);
            addItem.setVisible(false);
            addContainer.setVisible(false);
            delete.setVisible(false);
            attributes.setVisible(false);
            attributesPane.setVisible(false);
            System.out.println("true");
            if(getSelectedItemIndex() == 0){
                addDrone.setDisable(false);
                addItem.setDisable(false);
                addContainer.setDisable(false);
                delete.setDisable(true);
                attributes.setDisable(true);
                addDrone.setVisible(true);
                addItem.setVisible(true);
                addContainer.setVisible(true);
                delete.setVisible(false);
                attributes.setVisible(false);
                System.out.println("farm");
            }else if (getSelectedItem().hasNext() && getSelectedItem().getCommandCenter() == 1){
                addDrone.setDisable(true);
                addItem.setDisable(true);
                addContainer.setDisable(true);
                delete.setDisable(false);
                attributes.setDisable(false);
                addDrone.setVisible(false);
                addItem.setVisible(false);
                addContainer.setVisible(false);
                delete.setVisible(true);
                attributes.setVisible(true);
                System.out.println("command");
            }else if(getSelectedItem().getDrone() == 1){
                addDrone.setDisable(true);
                addItem.setDisable(true);
                addContainer.setDisable(true);
                delete.setDisable(true);
                attributes.setDisable(false);
                addDrone.setVisible(false);
                addItem.setVisible(false);
                addContainer.setVisible(false);
                delete.setVisible(false);
                attributes.setVisible(false);
                System.out.println("drone");
            }else if (getSelectedItem().getChildren() != null){
                addDrone.setDisable(true);
                addItem.setDisable(false);
                addContainer.setDisable(false);
                delete.setDisable(false);
                attributes.setDisable(false);
                addDrone.setVisible(false);
                addItem.setVisible(true);
                addContainer.setVisible(true);
                delete.setVisible(true);
                attributes.setVisible(true);
                System.out.println("container");
            }else {
                addDrone.setDisable(true);
                addItem.setDisable(true);
                addContainer.setDisable(true);
                delete.setDisable(false);
                attributes.setDisable(false);
                addDrone.setVisible(false);
                addItem.setVisible(false);
                addContainer.setVisible(false);
                delete.setVisible(true);
                attributes.setVisible(true);
                System.out.println("item");
            }
        }

    }
    @FXML
    private void droneMenu(){
        drone.setDisable(false);
        drone.setVisible(true);
        if (droneCount > 0){
            JOptionPane.showMessageDialog(null, "Only one Drone can be added",
                    "Drone Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        droneCount++;
        farmItemList.add(new ItemContainerCommandCenter("Command Center", 1, new ArrayList<ItemInfo>(){
            {
                add(new ItemLeafDrone("Drone", 1,0, 100, 100, 0, 0));
            }
        }, 0, 50,50,100,100, Color.BLACK, Color.BLACK));
        xPos = 100;
        yPos = 100;
        updateFarmItemList();
        MultipleSelectionModel selectionModel = treeView.getSelectionModel();
        selectionModel.select(0);
    }
    @FXML
    private void itemMenu(){
        double x;
        double y;
        if (getSelectedItemIndex() == 0){
            x = 300;
            y = 400;
        }else {
            x = getSelectedItem().getPosX();
            y = getSelectedItem().getPosY();
        }
        addItem(new ItemLeaf("New_Item", 0, x,y,5,5, Color.BLACK, Color.BLACK));
    }
    @FXML
    private void containerMenu(){
        double x;
        double y;
        if (getSelectedItemIndex() == 0){
            x = 300;
            y = 400;
        }else {
            x = getSelectedItem().getPosX();
            y = getSelectedItem().getPosY();
        }
        addItem(new ItemContainer("New_Container", new ArrayList<ItemInfo>(), 0, x,y,50,50, Color.BLACK, Color.BLACK));
    }
    @FXML
    private void deleteMenu(){
        ItemInfo deleteItem = getSelectedItem();
        if (deleteItem == null || String.valueOf(deleteItem) == "Farm" ) {
            return;
        }else if (deleteItem.getCommandCenter() == 1){
            droneCount--;
        }
        TreeItem<ItemInfo> item = treeView.getSelectionModel().getSelectedItem();
        TreeItem<ItemInfo> parent = item.getParent();
        if (String.valueOf(parent.getValue()) == "Farm"){
            farmItemList.remove(deleteItem);
        }else {
            parent.getValue().removeChild(deleteItem);
        }
        updateFarmItemList();
    }
    @FXML
    private void attributesMenu(){
        attributesPane.setVisible(true);
    }
    int getSelectedItemIndex(){
        return treeView.getSelectionModel().getSelectedIndex();
    }

    private void addItem(ItemInfo newItem){
        int index = getSelectedItemIndex();
        ItemInfo selection = getSelectedItem();
        if(selection != null && selection.getChildren()!= null){
            selection.addChild(newItem);
        }else {
            farmItemList.add(newItem);
        }
        updateFarmItemList();
        MultipleSelectionModel multipleSelection = treeView.getSelectionModel();
        multipleSelection.select(index);
    }
    private void updateFarmItemList(){
        treeView.getRoot().getChildren().clear();
        farmVisualization.getChildren().clear();
        farmItemList.forEach(itemInfo -> {
            addChildren(itemInfo, treeView.getRoot());
            drawTreeItems(itemInfo, treeView.getRoot());
        });
    }

    void addChildren(ItemInfo item, TreeItem<ItemInfo> parent) {
        TreeItem<ItemInfo> newItem = new TreeItem<ItemInfo>(item);
        parent.getChildren().add(newItem);
        while (item.hasNext()) {
            addChildren(item.next(), newItem);
        }
    }

    @FXML
    private void save() {
        ItemInfo selection = getSelectedItem();
        ItemInfo parent = getSelectedItemParent();
        int index = getSelectedItemIndex();
        if (nameField.getText() == "Farm"){
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n There can only be one farm.",
                    "InfoBox: Name Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }else if(parent.getName() == "Farm"){
            if (Double.parseDouble(posXField.getText()) + Double.parseDouble(lengthField.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value greater than zero",
                        "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (Double.parseDouble(posYField.getText()) + Double.parseDouble(widthField.getText()) <= 0) {
                JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value greater than zero",
                        "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }else{
            parent.getChildren().forEach(itemInfo ->{
                if(itemInfo.getWidth() > Double.parseDouble(widthField.getText())){
                    JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Width of container is smaller than its items\n Please enter a value greater than "
                                    + itemInfo.getWidth(),
                            "InfoBox: Width Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if(itemInfo.getLength() > Double.parseDouble(lengthField.getText())){
                    JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Length of container is smaller than its items\n Please enter a value greater than "
                                    + itemInfo.getLength(),
                            "InfoBox: Length Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

            });
            if (Double.parseDouble(posXField.getText()) < parent.getPosX()){
                JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n X Value is less than " + parent.getPosX() +
                                "\n Please enter a value higher than " + parent.getPosX(),
                        "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (Double.parseDouble(posXField.getText()) > parent.getPosX() + parent.getLength()){
                JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n X Value is greater than the length of the container.\n Please enter a value less than " + (parent.getPosX() + parent.getLength()),
                        "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (Double.parseDouble(posYField.getText()) < parent.getPosY()){
                JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Y Value is less than " + parent.getPosY() +
                                "Putting it outside of its container.\n Please enter a value higher than " + parent.getPosY(),
                        "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (Double.parseDouble(posYField.getText()) > parent.getPosY() + parent.getWidth()){
                JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Y Value is greater than the length of the container.\n Please enter a value less than " + (parent.getPosY() + parent.getWidth()),
                        "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (Double.parseDouble(lengthField.getText()) > parent.getLength()){
                JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n length value is greater than " + parent.getLength() +
                                "\n Please enter a value less than " + parent.getLength(),
                        "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (Double.parseDouble(posXField.getText()) + Double.parseDouble(lengthField.getText()) > parent.getPosX() + parent.getLength()){
                JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n X Value plus length is greater than the X coordinate plus the length of the container:" + (parent.getPosX() + parent.getLength()) +
                                "\n Please enter a value higher than " + parent.getLength(),
                        "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (Double.parseDouble(widthField.getText()) > parent.getWidth()){
                JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Y Value is greater than " + parent.getPosY() +
                                "\n Please enter a value higher than " + parent.getWidth(),
                        "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (Double.parseDouble(posYField.getText()) + Double.parseDouble(widthField.getText()) > parent.getPosY() + parent.getWidth()){
                JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Y Value plus width is greater than the Y coordinate plus the width of the container:" + (parent.getPosY() + parent.getWidth()) +
                                "\n Please enter a value higher than " + parent.getWidth(),
                        "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        if (Double.parseDouble(posXField.getText()) + Double.parseDouble(lengthField.getText()) > 600) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\nBeginning X Value and Length of Item extends outside of farm area for Farm Item: " + selection.getName() +
                            "\nPlease add a valid X Value and Length that add up to be no more than 600",
                    "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (Double.parseDouble(posYField.getText()) + Double.parseDouble(widthField.getText()) > 800) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\nBeginning Y Value and Width of Item extends outside of farm area for Farm Item: " + selection.getName() +
                            "\nPlease add a valid Y Value and Width that add up to be no more than 800",
                    "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        selection.setName(nameField.getText());
        selection.setPos(Double.parseDouble(posXField.getText()), Double.parseDouble(posYField.getText()));
        selection.setDimension(Double.parseDouble(lengthField.getText()), Double.parseDouble(widthField.getText()));
        selection.setValue(Double.parseDouble(valueField.getText()));
        selection.setFontColor(fontColorID.getValue());
        selection.setContainerColor(containerColorID.getValue());
        treeView.refresh();
        attributesPane.setVisible(false);
        updateFarmItemList();
        MultipleSelectionModel selectionModel = treeView.getSelectionModel();
        selectionModel.select(index);
    }

    private void drawItem(ItemInfo item) {
        Rectangle rect = new Rectangle(item.getPosX(), item.getPosY(), item.getLength(), item.getWidth());
        rect.setAccessibleText(item.getName());
        rect.setStroke(item.getContainerColor());
        rect.setStrokeWidth(2);
        rect.setFill(Color.TRANSPARENT);
        farmVisualization.getChildren().add(rect);
    }

    private void drawItemContainer(ItemInfo itemContainer) {
        Rectangle rect = new Rectangle(itemContainer.getPosX(), itemContainer.getPosY(), itemContainer.getLength(), itemContainer.getWidth());
        rect.setAccessibleText(itemContainer.getName());
        rect.setStroke(itemContainer.getContainerColor());
        rect.setStrokeWidth(5);
        rect.setFill(Color.TRANSPARENT);
        farmVisualization.getChildren().add(rect);
    }
    void drawTreeItems(ItemInfo item, TreeItem<ItemInfo> parent) {
        TreeItem<ItemInfo> newTreeItem = new TreeItem<ItemInfo>(item);
        Text text = new Text(item.getPosX(), item.getPosY() - 5, item.getName());
        text.setAccessibleText(item.getName());
        text.setFill(item.getFontColor());
        if (item != null && item.getChildren() != null)
        {
            farmVisualization.getChildren().add(text);
            drawItemContainer(item);
        } else {
            if (item.getName().equals("Drone")) {
                drone.setX(item.getPosX() - 50);
                drone.setY(item.getPosY() - 50);
                farmVisualization.getChildren().add(drone);
            }
            else {
                farmVisualization.getChildren().add(text);
                drawItem(item);
            }
        }
        while (item.hasNext()) {
            drawTreeItems(item.next(), newTreeItem);
        }
    }


    @FXML
    private void visitItem() {
        System.out.println(getSelectedItem().getPosX());
        System.out.println(getSelectedItem().getPosY());
        System.out.println(xPos);
        System.out.println(yPos);
        if(getSelectedItem().getLength()/2 + getSelectedItem().getPosX() == xPos && getSelectedItem().getWidth()/2 + getSelectedItem().getPosY() == yPos){
            System.out.println("ran");
            return;
        }
        Path scan = new Path();
        scan.getElements().add(new MoveTo(xPos, yPos));
        scan.getElements().add(new LineTo(getSelectedItem().getLength()/2 + getSelectedItem().getPosX(), getSelectedItem().getWidth()/2 + getSelectedItem().getPosY()));
        PathTransition scantransition = new PathTransition();
        scantransition.setNode(drone);
        scantransition.setDuration(Duration.millis(3000));
        scantransition.setPath(scan);
        scantransition.setCycleCount(1);
        scantransition.play();
        xPos = getSelectedItem().getLength()/2 + getSelectedItem().getPosX();
        yPos = getSelectedItem().getWidth()/2 + getSelectedItem().getPosY();
        visitButton.setDisable(true);
    }

    @FXML
    private void scanFarmClick(){
        int point = 600;
        if (droneCount == 0){
            return;
        }
        Path scan = new Path();
        scan.getElements().add(new MoveTo(xPos, yPos));
        scan.getElements().add(new LineTo(25, 25));
        while (true){
            point = point - 25;
            scan.getElements().add(new HLineTo(point));
            scan.getElements().add(new VLineTo(775));
            if (point == 25){
                break;
            }
            point = point - 25;
            scan.getElements().add(new HLineTo(point));
            scan.getElements().add(new VLineTo(25));
        }
        System.out.println(getSelectedItem().getLength()/2 + getSelectedItem().getPosX());
        System.out.println(getSelectedItem().getWidth()/2 + getSelectedItem().getPosY());
        scan.getElements().add(new VLineTo(getSelectedItem().getWidth()/2 + getSelectedItem().getPosY()));
        scan.getElements().add(new HLineTo(getSelectedItem().getLength()/2 + getSelectedItem().getPosX()));
        PathTransition scantransition = new PathTransition();
        scantransition.setNode(drone);
        scantransition.setDuration(Duration.millis(15000));
        scantransition.setPath(scan);
        scantransition.setCycleCount(1);
        scantransition.play();
        scanFarmButton.setDisable(true);
        visitButton.setDisable(true);
        scanFarmButton.setText("Scanning Farm...");
        scantransition.setOnFinished(event -> {
            visitButton.setDisable(false);
            scanFarmButton.setDisable(false);
            scanFarmButton.setText("Scan Farm");
        } );
        xPos = getSelectedItem().getLength()/2 + getSelectedItem().getPosX();
        yPos = getSelectedItem().getWidth()/2 + getSelectedItem().getPosY();
    }
}