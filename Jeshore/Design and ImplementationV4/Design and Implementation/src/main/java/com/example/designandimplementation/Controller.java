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
import java.util.Objects;


import javafx.util.Duration;
import javafx.scene.text.Text;
import items.*;

import static java.lang.Math.round;

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
    private AnchorPane namePane;
    @FXML
    private AnchorPane positionPane;

    @FXML
    private AnchorPane sizePane;

    @FXML
    private AnchorPane valuePane;

    @FXML
    private AnchorPane colorPane;
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
    private MenuItem name;
    @FXML
    private MenuItem position;
    @FXML
    private MenuItem size;
    @FXML
    private MenuItem value;
    @FXML
    private MenuItem color;
    @FXML
    private ColorPicker fontColorID;
    @FXML
    private ColorPicker containerColorID;
    @FXML
    private Label nameAtt;
    @FXML
    private Label posAtt;
    @FXML
    private Label sizeAtt;
    @FXML
    private Label priceAtt;
    @FXML
    private Label fontAtt;
    @FXML
    private Label containerAtt;



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
                    setValues();
                });
    }
    void setValues(){
        if (getSelectedItem() == null){
            return;
        }
        ItemInfo selection = getSelectedItem();
        int index = getSelectedItemIndex();
        nameField.setText(selection.getName());
        posXField.setText(String.valueOf(selection.getPosX()));
        posYField.setText(String.valueOf(selection.getPosY()));
        lengthField.setText(String.valueOf(selection.getLength()));
        widthField.setText(String.valueOf(selection.getWidth()));
        valueField.setText(String.valueOf(selection.getValue()));
        containerColorID.setValue(selection.getContainerColor());
        fontColorID.setValue(selection.getFontColor());
        if (index == 0){
            posAtt.setText("N/A");
            sizeAtt.setText("(800, 600)");
            fontAtt.setText("N/A");
            containerAtt.setText("N/A");
        }else {
            posAtt.setText("(" + round(selection.getPosX()) + ", " + round(selection.getPosY()) + ")");
            sizeAtt.setText("(" + round(selection.getLength()) + ", " + round(selection.getWidth()) + ")");
            fontAtt.setText(String.valueOf(selection.getFontColor()));
            containerAtt.setText(String.valueOf(selection.getContainerColor()));
        }
        nameAtt.setText(selection.getName());
        priceAtt.setText(String.valueOf(selection.getValue()));
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
            namePane.setVisible(false);
            positionPane.setVisible(false);
            sizePane.setVisible(false);
            valuePane.setVisible(false);
            colorPane.setVisible(false);
            visitButton.setDisable(true);
            scanFarmButton.setDisable(true);
            attributesPane.setVisible(true);
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
                if (droneCount == 1){
                    visitButton.setDisable(false);
                }
                visitButton.setText("Visit " + getSelectedItem().getName());
            }else{
                if (droneCount == 1){
                    visitButton.setDisable(false);
                }
                visitButton.setText("Visit " + getSelectedItem().getName());
            }
        }
        if (event.isSecondaryButtonDown()){
            scanFarmButton.setDisable(true);
            visitButton.setDisable(true);
            namePane.setVisible(false);
            positionPane.setVisible(false);
            sizePane.setVisible(false);
            valuePane.setVisible(false);
            colorPane.setVisible(false);
            attributesPane.setVisible(true);
            System.out.println("true");
            if(getSelectedItemIndex() == 0){
                addDrone.setDisable(false);
                addItem.setDisable(false);
                addContainer.setDisable(false);
                delete.setDisable(true);
                name.setDisable(true);
                position.setDisable(true);
                size.setDisable(true);
                value.setDisable(false);
                color.setDisable(true);

                addDrone.setVisible(true);
                addItem.setVisible(true);
                addContainer.setVisible(true);
                delete.setVisible(false);
                name.setVisible(false);
                position.setVisible(false);
                size.setVisible(false);
                value.setVisible(true);
                color.setVisible(false);

                System.out.println("farm");
            }else if (getSelectedItem().hasNext() && getSelectedItem().getCommandCenter() == 1){
                addDrone.setDisable(true);
                addItem.setDisable(true);
                addContainer.setDisable(true);
                delete.setDisable(false);
                name.setDisable(false);
                position.setDisable(false);
                size.setDisable(false);
                value.setDisable(false);
                color.setDisable(false);

                addDrone.setVisible(false);
                addItem.setVisible(false);
                addContainer.setVisible(false);
                delete.setVisible(true);
                name.setVisible(true);
                position.setVisible(true);
                size.setVisible(true);
                value.setVisible(true);
                color.setVisible(true);

                System.out.println("command");
            }else if(getSelectedItem().getDrone() == 1){
                addDrone.setDisable(true);
                addItem.setDisable(true);
                addContainer.setDisable(true);
                delete.setDisable(true);
                name.setDisable(true);
                position.setDisable(true);
                size.setDisable(true);
                value.setDisable(false);
                color.setDisable(true);

                addDrone.setVisible(false);
                addItem.setVisible(false);
                addContainer.setVisible(false);
                delete.setVisible(false);
                name.setVisible(false);
                position.setVisible(false);
                size.setVisible(false);
                value.setVisible(true);
                color.setVisible(false);

                System.out.println("drone");
            }else if (getSelectedItem().getChildren() != null){
                addDrone.setDisable(true);
                addItem.setDisable(false);
                addContainer.setDisable(false);
                delete.setDisable(false);
                name.setDisable(false);
                position.setDisable(false);
                size.setDisable(false);
                value.setDisable(false);
                color.setDisable(false);

                addDrone.setVisible(false);
                addItem.setVisible(true);
                addContainer.setVisible(true);
                delete.setVisible(true);
                name.setVisible(true);
                position.setVisible(true);
                size.setVisible(true);
                value.setVisible(true);
                color.setVisible(true);

                System.out.println("container");
            }else {
                addDrone.setDisable(true);
                addItem.setDisable(true);
                addContainer.setDisable(true);
                delete.setDisable(false);
                name.setDisable(false);
                position.setDisable(false);
                size.setDisable(false);
                value.setDisable(false);
                color.setDisable(false);

                addDrone.setVisible(false);
                addItem.setVisible(false);
                addContainer.setVisible(false);
                delete.setVisible(true);
                name.setVisible(true);
                position.setVisible(true);
                size.setVisible(true);
                value.setVisible(true);
                color.setVisible(true);

                System.out.println("item");
            }
        }

    }
    @FXML
    private void droneMenu(){
        attributesPane.setVisible(false);
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
        attributesPane.setVisible(false);
        double x;
        double y;
        if (getSelectedItemIndex() == 0){
            x = 400;
            y = 300;
        }else {
            x = getSelectedItem().getPosX();
            y = getSelectedItem().getPosY();
        }
        addItem(new ItemLeaf("New_Item", 0, x,y,5,5, Color.BLACK, Color.BLACK));
    }
    @FXML
    private void containerMenu(){
        attributesPane.setVisible(false);
        double x;
        double y;
        if (getSelectedItemIndex() == 0){
            x = 400;
            y = 300;
        }else {
            x = getSelectedItem().getPosX();
            y = getSelectedItem().getPosY();
        }
        addItem(new ItemContainer("New_Container", new ArrayList<ItemInfo>(), 0, x,y,50,50, Color.BLACK, Color.BLACK));
    }
    @FXML
    private void deleteMenu(){
        attributesPane.setVisible(false);
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
    private void nameMenu(){
        attributesPane.setVisible(false);
        namePane.setVisible(true);
    }
    @FXML
    private void positionMenu(){
        attributesPane.setVisible(false);
        positionPane.setVisible(true);
    }
    @FXML
    private void sizeMenu(){
        attributesPane.setVisible(false);
        sizePane.setVisible(true);
    }
    @FXML
    private void valueMenu(){
        attributesPane.setVisible(false);
        valuePane.setVisible(true);
    }
    @FXML
    private void colorMenu(){
        attributesPane.setVisible(false);
        colorPane.setVisible(true);
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
    private void nameSave(){
        ItemInfo selection = getSelectedItem();
        int index = getSelectedItemIndex();
        if (Objects.equals(nameField.getText().toLowerCase(), "farm")){
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n There can only be one farm.",
                    "InfoBox: Name Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        selection.setName(nameField.getText());
        namePane.setVisible(false);
        setValues();
        treeView.refresh();
        updateFarmItemList();
        MultipleSelectionModel selectionModel = treeView.getSelectionModel();
        selectionModel.select(index);
    }
    @FXML
    private void positionSave(){
        ItemInfo selection = getSelectedItem();
        ItemInfo parent = getSelectedItemParent();
        int index = getSelectedItemIndex();
        ArrayList<ItemInfo> child = selection.getChildren();
        int i = 0;
        minMaxCheck(selection);
        double lastPosX = selection.getPosX();
        double lastPosY = selection.getPosY();
        double differenceX;
        double differenceY;
        if (lastPosX == Double.parseDouble(posXField.getText())){
            differenceX = 0;
        } else {
            differenceX = Double.parseDouble(posXField.getText()) - lastPosX;
        }
        if (lastPosY == Double.parseDouble(posYField.getText())){
            differenceY = 0;
        } else {
            differenceY = Double.parseDouble(posYField.getText()) - lastPosY;
        }
        System.out.println(differenceX);
        System.out.println(differenceY);
        if (child != null) {
            while (i < child.size()){
                child.get(i).setPos(child.get(i).getPosX()+differenceX, child.get(i).getPosY()+differenceY);
                i++;
            }

        }
        if(!parent.getName().equalsIgnoreCase("farm")){
            if (parentPositionError(parent, selection)){
                return;
            }
        }

        selection.setPos(Double.parseDouble(posXField.getText()), Double.parseDouble(posYField.getText()));
        positionPane.setVisible(false);
        setValues();
        treeView.refresh();
        updateFarmItemList();
        MultipleSelectionModel selectionModel = treeView.getSelectionModel();
        selectionModel.select(index);
    }
    @FXML
    private void sizeSave(){
        ItemInfo selection = getSelectedItem();
        ItemInfo parent = getSelectedItemParent();
        int index = getSelectedItemIndex();
        ArrayList<ItemInfo> child = selection.getChildren();
        sizeCheck(selection);

        int i = 0;

        double childLengthMax = 0;
        double childWidthMax = 0;


        if (child != null) {
            while (i < child.size()) {
                    if (child.get(i).getLength() + child.get(i).getPosX() > childLengthMax) {
                        childLengthMax = child.get(i).getLength() + child.get(i).getPosX();
                    }
                    if (child.get(i).getWidth() + child.get(i).getPosY() > childWidthMax) {
                        childWidthMax = child.get(i).getWidth() + child.get(i).getPosY();
                    }
                i++;
            }
            if(childrenSizeCheck(childLengthMax, childWidthMax, parent, selection)){
                return;
            }
        }
        if(!parent.getName().equalsIgnoreCase("farm")){
            if (parentSizeError(parent, selection)){
                return;
            }
        }
        selection.setDimension(Double.parseDouble(lengthField.getText()), Double.parseDouble(widthField.getText()));
        sizePane.setVisible(false);
        setValues();
        treeView.refresh();
        updateFarmItemList();
        MultipleSelectionModel selectionModel = treeView.getSelectionModel();
        selectionModel.select(index);
    }
    @FXML
    private void valueSave(){
        ItemInfo selection = getSelectedItem();
        int index = getSelectedItemIndex();
        selection.setValue(Double.parseDouble(valueField.getText()));
        valuePane.setVisible(false);
        setValues();
        treeView.refresh();
        updateFarmItemList();
        MultipleSelectionModel selectionModel = treeView.getSelectionModel();
        selectionModel.select(index);
    }
    @FXML
    private void colorSave(){
        ItemInfo selection = getSelectedItem();
        int index = getSelectedItemIndex();
        selection.setFontColor(fontColorID.getValue());
        selection.setContainerColor(containerColorID.getValue());
        colorPane.setVisible(false);
        setValues();
        treeView.refresh();
        updateFarmItemList();
        MultipleSelectionModel selectionModel = treeView.getSelectionModel();
        selectionModel.select(index);
    }

    void minMaxCheck(ItemInfo selection){
        if (Double.parseDouble(posXField.getText()) + selection.getLength() <= 0) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value greater than zero",
                    "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (Double.parseDouble(posYField.getText()) + selection.getWidth() <= 0) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value greater than zero",
                    "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (Double.parseDouble(posXField.getText()) + selection.getLength() > 800) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved \nX Value and Length add up to be no more than 800",
                    "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (Double.parseDouble(posYField.getText()) + selection.getWidth() > 600) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved \nY Value and Width add up to be no more than 600",
                    "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }
    void sizeCheck(ItemInfo selection){
        if (Double.parseDouble(lengthField.getText()) + selection.getPosX() <= 0) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value greater than zero",
                    "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (Double.parseDouble(widthField.getText()) + selection.getPosY() <= 0) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value greater than zero",
                    "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (Double.parseDouble(lengthField.getText()) + selection.getPosX() > 800) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved \nX Value and Length add up to be no more than 800",
                    "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (Double.parseDouble(widthField.getText()) + selection.getPosY() > 600) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved \nY Value and Width add up to be no more than 600",
                    "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }
    boolean parentPositionError(ItemInfo parent, ItemInfo selection){
        if (Double.parseDouble(posXField.getText()) < parent.getPosX()){
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value greater than the minimum of the container " + parent.getPosX(),
                    "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(posYField.getText()) < parent.getPosY()){
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value greater than the minimum of the container " + parent.getPosY(),
                    "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(posXField.getText()) + selection.getLength() > parent.getPosX() + parent.getLength()){
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please either change the item's size or enter a value lower than the maximum of the container " + ((parent.getPosX() + parent.getLength()) - selection.getLength()),
                    "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(posYField.getText()) + selection.getWidth() > parent.getPosY() + parent.getWidth()){
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please either change the item's size or enter a value lower than the maximum of the container " + ((parent.getPosY() + parent.getWidth()) - selection.getWidth()),
                    "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
    boolean parentSizeError(ItemInfo parent, ItemInfo selection){
        if (Double.parseDouble(lengthField.getText()) + selection.getPosX() >= parent.getPosX() + parent.getLength()){
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value less than " + (parent.getLength() - (selection.getPosX() - parent.getPosX())),
                    "InfoBox: Length Size Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

        if (Double.parseDouble(widthField.getText()) + selection.getPosY() >= parent.getPosY() + parent.getWidth()){
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value less than " + (parent.getWidth() - (selection.getPosY() - parent.getPosY())),
                    "InfoBox: Width Size Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
    boolean childrenSizeCheck(double childLengthMax, double childWidthMax, ItemInfo parent, ItemInfo selection){
        if (Double.parseDouble(lengthField.getText()) + parent.getPosX() < childLengthMax){
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value greater than " + (childLengthMax - parent.getPosX()),
                    "InfoBox: Length Size Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

        if (Double.parseDouble(lengthField.getText()) + parent.getPosY() < childWidthMax){
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value greater than " + (childWidthMax - parent.getPosY()),
                    "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
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