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
    private double xPos;
    private double yPos;

    private static Controller instance;

    public static synchronized Controller getInstance(){
        if (instance == null){
            instance = new Controller();
        }
        System.out.println("out");
        return instance;

    }
    public void testInstance(){
        System.out.println("Test Instance");
    }
    @FXML
    public void initialize() {

        farmItemList = new ArrayList<ItemInfo>();
        ItemLeaf farm = new ItemLeaf("Farm", 0, 0,0,0,0);
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
                });
    }
    ItemInfo getSelectedItem(){
        TreeItem<ItemInfo> itemSelected = treeView.getSelectionModel().getSelectedItem();
        if (itemSelected == null){
            return null;
        }
        return itemSelected.getValue();
    }
    @FXML
    private void treeViewClick(MouseEvent event){
        if (event.isPrimaryButtonDown()){
            System.out.println("false");
            if (getSelectedItem() == null){
                scanFarmButton.setDisable(true);
                visitButton.setDisable(true);
                attributesPane.setVisible(false);
            }else if(getSelectedItemIndex() == 0){
                scanFarmButton.setDisable(true);
                visitButton.setDisable(true);
                attributesPane.setVisible(false);
            }else if (getSelectedItem().hasNext() && getSelectedItem().getCommandCenter() == 1){
                scanFarmButton.setDisable(false);
                visitButton.setDisable(false);
                attributesPane.setVisible(false);
            }else if(getSelectedItem().getDrone() == 1){
                scanFarmButton.setDisable(true);
                visitButton.setDisable(true);
                attributesPane.setVisible(false);
            }else if (getSelectedItem().getChildren() != null){
                scanFarmButton.setDisable(true);
                visitButton.setDisable(false);
                attributesPane.setVisible(false);
            }else {
                scanFarmButton.setDisable(true);
                visitButton.setDisable(false);
                attributesPane.setVisible(false);
            }
        }
        if (event.isSecondaryButtonDown()){
            System.out.println("true");
            if (getSelectedItem() == null){
                scanFarmButton.setDisable(true);
                visitButton.setDisable(true);
                addDrone.setVisible(false);
                addItem.setVisible(false);
                addContainer.setVisible(false);
                delete.setVisible(false);
                attributesPane.setVisible(false);
                System.out.println("null");
            }else if(getSelectedItemIndex() == 0){
                scanFarmButton.setDisable(true);
                visitButton.setDisable(true);
                addDrone.setVisible(true);
                addItem.setVisible(true);
                addContainer.setVisible(true);
                delete.setVisible(true);
                attributes.setVisible(false);
                attributesPane.setVisible(false);
                System.out.println("farm");
            }else if (getSelectedItem().hasNext() && getSelectedItem().getCommandCenter() == 1){
                scanFarmButton.setDisable(true);
                visitButton.setDisable(true);
                addDrone.setVisible(false);
                addItem.setVisible(false);
                addContainer.setVisible(false);
                delete.setVisible(true);
                attributes.setVisible(true);
                attributesPane.setVisible(false);
                System.out.println("command");
            }else if(getSelectedItem().getDrone() == 1){
                scanFarmButton.setDisable(true);
                visitButton.setDisable(true);
                addDrone.setVisible(false);
                addItem.setVisible(false);
                addContainer.setVisible(false);
                delete.setVisible(false);
                attributes.setVisible(true);
                attributesPane.setVisible(false);
                System.out.println("drone");
            }else if (getSelectedItem().getChildren() != null){
                scanFarmButton.setDisable(true);
                visitButton.setDisable(true);
                addDrone.setVisible(false);
                addItem.setVisible(true);
                addContainer.setVisible(true);
                delete.setVisible(true);
                attributes.setVisible(true);
                attributesPane.setVisible(false);
                System.out.println("container");
            }else {
                scanFarmButton.setDisable(true);
                visitButton.setDisable(true);
                addDrone.setVisible(false);
                addItem.setVisible(false);
                addContainer.setVisible(false);
                delete.setVisible(true);
                attributes.setVisible(true);
                attributesPane.setVisible(false);
                System.out.println("item");
            }
        }

    }
    @FXML
    private void droneMenu(){
        drone.setVisible(true);
        if (droneCount > 0){
            JOptionPane.showMessageDialog(null, "Only one Drone can be added",
                    "Drone Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        droneCount++;
        farmItemList.add(new ItemContainerCommandCenter("Command Center", 1, new ArrayList<ItemInfo>(){
            {
                add(new ItemLeafDrone("Drone", 1,0, 50, 50, 0, 0));
            }
        }, 0, 50,50,100,100));
        xPos = 100;
        yPos = 100;
        updateFarmItemList();
        MultipleSelectionModel selectionModel = treeView.getSelectionModel();
        selectionModel.select(0);
    }
    @FXML
    private void itemMenu(){
        addItem(new ItemLeaf("New_Item", 0, 0,0,0,0));
    }
    @FXML
    private void containerMenu(){
        addItem(new ItemContainer("New_ItemContainer", new ArrayList<ItemInfo>(), 0, 0,0,0,0));
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
        int itemSelectedIndex = treeView.getSelectionModel().getSelectedIndex();
        return itemSelectedIndex;
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

    void addChildren(ItemInfo item, TreeItem<ItemInfo> parent){
        TreeItem<ItemInfo> newItem = new TreeItem<ItemInfo>(item);
        parent.getChildren().add(newItem);
        while (item.hasNext()){
            addChildren(item.next(), newItem);
        }
    }

    @FXML
    private void save() {
        ItemInfo selection = getSelectedItem();
        int index = getSelectedItemIndex();
        selection.setName(nameField.getText());
        selection.setPos(Integer.parseInt(posXField.getText()), Integer.parseInt(posYField.getText()));
        selection.setDimension(Integer.parseInt(lengthField.getText()), Integer.parseInt(widthField.getText()));
        selection.setValue(Double.parseDouble(valueField.getText()));


        if (selection.getPosX() + selection.getLength() >= 600)
        {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\nBeginning X Value and Length of Item extends outside of farm area for Farm Item: " + selection.getName() +
                            "\nPlease add a valid X Value and Length that add up to be less than 600",
                    "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (selection.getPosY() + selection.getWidth() >= 800)
        {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\nBeginning Y Value and Width of Item extends outside of farm area for Farm Item: " + selection.getName() +
                            "\nPlease add a valid Y Value and Width that add up to be less than 800",
                    "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        treeView.refresh();
        updateFarmItemList();
        MultipleSelectionModel selectionModel = treeView.getSelectionModel();
        selectionModel.select(index);
    }

    private void drawItem(ItemInfo item) {
        Rectangle rect = new Rectangle(item.getPosX(), item.getPosY(), item.getLength(), item.getWidth());
        rect.setAccessibleText(item.getName());
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(2);
        rect.setFill(Color.TRANSPARENT);
        farmVisualization.getChildren().add(rect);
    }

    private void drawItemContainer(ItemInfo itemContainer) {
        Rectangle rect = new Rectangle(itemContainer.getPosX(), itemContainer.getPosY(), itemContainer.getLength(), itemContainer.getWidth());
        rect.setAccessibleText(itemContainer.getName());
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(5);
        rect.setFill(Color.TRANSPARENT);
        farmVisualization.getChildren().add(rect);
    }
    void drawTreeItems(ItemInfo item, TreeItem<ItemInfo> parent) {
        TreeItem<ItemInfo> newTreeItem = new TreeItem<ItemInfo>(item);
        Text text = new Text(item.getPosX(), item.getPosY() - 5, item.getName());
        text.setAccessibleText(item.getName());
        if (item != null && item.getChildren() != null)
        {
            farmVisualization.getChildren().add(text);
            drawItemContainer(item);
        } else {
            if (item.getName().equals("Drone")) {
                drone.setX(item.getPosX());
                drone.setY(item.getPosY());
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
        Path scan = new Path();
        scan.getElements().add(new MoveTo(xPos, yPos));
        scan.getElements().add(new LineTo(getSelectedItem().getLength()/2f + getSelectedItem().getPosX(), getSelectedItem().getWidth()/2f + getSelectedItem().getPosY()));
        PathTransition scantransition = new PathTransition();
        scantransition.setNode(drone);
        scantransition.setDuration(Duration.millis(3000));
        scantransition.setPath(scan);
        scantransition.setCycleCount(1);
        scantransition.play();
        xPos = getSelectedItem().getLength()/2f + getSelectedItem().getPosX();
        yPos = getSelectedItem().getWidth()/2f + getSelectedItem().getPosY();
        visitButton.setDisable(true);
    }

    @FXML
    private void scanFarmClick(){
        if (droneCount == 0){
            return;
        }
        Path scan = new Path();
        scan.getElements().add(new MoveTo(25, 25));
        scan.getElements().add(new HLineTo(575));
        scan.getElements().add(new VLineTo(775));
        scan.getElements().add(new HLineTo(550));
        scan.getElements().add(new VLineTo(25));
        scan.getElements().add(new HLineTo(550));
        scan.getElements().add(new VLineTo(775));
        scan.getElements().add(new HLineTo(525));
        scan.getElements().add(new VLineTo(25));
        scan.getElements().add(new HLineTo(500));
        scan.getElements().add(new VLineTo(775));
        scan.getElements().add(new HLineTo(500));
        scan.getElements().add(new VLineTo(25));
        scan.getElements().add(new HLineTo(475));
        scan.getElements().add(new VLineTo(775));
        scan.getElements().add(new HLineTo(450));
        scan.getElements().add(new VLineTo(25));
        scan.getElements().add(new HLineTo(425));
        scan.getElements().add(new VLineTo(775));
        scan.getElements().add(new HLineTo(400));
        scan.getElements().add(new VLineTo(25));
        scan.getElements().add(new HLineTo(375));
        scan.getElements().add(new VLineTo(775));
        scan.getElements().add(new HLineTo(350));
        scan.getElements().add(new VLineTo(25));
        scan.getElements().add(new HLineTo(325));
        scan.getElements().add(new VLineTo(775));
        scan.getElements().add(new HLineTo(300));
        scan.getElements().add(new VLineTo(25));
        scan.getElements().add(new HLineTo(275));
        scan.getElements().add(new VLineTo(775));
        scan.getElements().add(new HLineTo(250));
        scan.getElements().add(new VLineTo(25));
        scan.getElements().add(new HLineTo(225));
        scan.getElements().add(new VLineTo(775));
        scan.getElements().add(new HLineTo(200));
        scan.getElements().add(new VLineTo(25));
        scan.getElements().add(new HLineTo(175));
        scan.getElements().add(new VLineTo(775));
        scan.getElements().add(new HLineTo(150));
        scan.getElements().add(new VLineTo(25));
        scan.getElements().add(new HLineTo(125));
        scan.getElements().add(new VLineTo(775));
        scan.getElements().add(new HLineTo(100));
        scan.getElements().add(new VLineTo(25));
        scan.getElements().add(new HLineTo(75));
        scan.getElements().add(new VLineTo(775));
        scan.getElements().add(new HLineTo(50));
        scan.getElements().add(new VLineTo(25));
        scan.getElements().add(new HLineTo(25));
        scan.getElements().add(new VLineTo(775));
        scan.getElements().add(new VLineTo(25));
        scan.getElements().add(new VLineTo(getSelectedItem().getLength()/2f + getSelectedItem().getPosX()));
        scan.getElements().add(new HLineTo(getSelectedItem().getWidth()/2f + getSelectedItem().getPosY()));
        PathTransition scantransition = new PathTransition();
        scantransition.setNode(drone);
        scantransition.setDuration(Duration.millis(10000));
        scantransition.setPath(scan);
        scantransition.setCycleCount(1);
        scantransition.play();
        scanFarmButton.setDisable(true);
        scanFarmButton.setText("Scanning Farm...");
        scantransition.setOnFinished(event -> {
            scanFarmButton.setDisable(false);
            scanFarmButton.setText("Scan Farm");
        } );
        xPos = getSelectedItem().getLength()/2f + getSelectedItem().getPosX();
        yPos = getSelectedItem().getWidth()/2f + getSelectedItem().getPosY();
    }
}