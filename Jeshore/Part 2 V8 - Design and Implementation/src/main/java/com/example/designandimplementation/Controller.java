package com.example.designandimplementation;

import javax.swing.*;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;


import javafx.scene.text.Text;
import items.*;

import static com.example.designandimplementation.Application.scene;


public class Controller {
    public static int droneCount = 0; //Keeps drone count only one drone allowed
    private ArrayList<ItemInfo> farmItemList; //List of items on farm
    @FXML
    public TreeView<ItemInfo> treeView; // Treeview with iteminfo
    @FXML
    public ImageView drone; //Drone
    @FXML
    private AnchorPane farmVisualization; //Farm Map
    @FXML
    //Text fields for user entries
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
    private TextField priceField;
    @FXML
    private TextField marketValueField;
    @FXML
    public RadioButton simulationRadioButton;
    @FXML
    public RadioButton droneRadioButton;
    @FXML
    public Button startButton;
    @FXML
    //Anchorpanes for each attribute
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
    private AnchorPane marketValuePane;
    @FXML
    private AnchorPane attributesPane;
    @FXML
    //Menu items shown on right click in treeview
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
    private MenuItem price;
    @FXML
    private MenuItem color;
    @FXML
    private MenuItem marketValue;
    @FXML
    //color pickers
    private ColorPicker fontColorID;
    @FXML
    private ColorPicker containerColorID;
    @FXML
    private ColorPicker fillColorID;
    @FXML
    //Attribute labels
    private Label nameAtt;
    @FXML
    private Label posAtt;
    @FXML
    private Label sizeAtt;
    @FXML
    private Label priceAtt;
    @FXML
    private Label marketValueAtt;
    @FXML
    private Label priceAllAtt;
    @FXML
    private Label marketValueAllAtt;
    @FXML
    private Label fontAtt;
    @FXML
    private Label containerAtt;
    @FXML
    private Label fillAtt;
    @FXML
    private SplitPane sp1;
    @FXML
    private SplitPane sp2;
    @FXML
    private SplitPane sp3;
    //Position of objects on map
    public static double xPos;
    public static double yPos;
    public static double droneXPos;
    public static double droneYPos;
    private final DecimalFormat df = new DecimalFormat("0.00");
    public static double totalFarmPrice = 0;
    public static double totalFarmMarketValue = 0;
    private int itemIndex = 0;
    private int itemChildIndex = 0;
    private int containerIndex = 0;
    private int containerChildIndex = 0;



    @FXML
    public void initialize() { //Initializes the tree view and updates it on change
        Singleton.getInstance();
        farmItemList = new ArrayList<>();
        ItemLeaf farm = new ItemLeaf("Farm", 0, 0, 0, 0, 0, 0, Color.BLACK, Color.BLACK, Color.WHITE);
        TreeItem<ItemInfo> rootItem = new TreeItem<>(farm);
        rootItem.setExpanded(true);
        treeView.setRoot(rootItem);
        sp1.getDividers().get(0).positionProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue ) ->
                        sp1.getDividers().get(0).setPosition(0.5));
        sp2.getDividers().get(0).positionProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue ) ->
                        sp2.getDividers().get(0).setPosition(0.75));
        sp3.getDividers().get(0).positionProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue ) ->
                        sp3.getDividers().get(0).setPosition(0.91));
    }


    void setValues() { //Sets current values to fields and attributes
        if (getSelectedItem() == null) {
            return;
        }
        ItemInfo selection = getSelectedItem();
        nameField.setText(selection.getName());
        posXField.setText(String.valueOf(df.format(selection.getPosX())));
        posYField.setText(String.valueOf(df.format(selection.getPosY())));
        lengthField.setText(String.valueOf(df.format(selection.getLength())));
        widthField.setText(String.valueOf(df.format(selection.getWidth())));
        priceField.setText(String.valueOf(df.format(selection.getPrice())));
        marketValueField.setText(String.valueOf(df.format(selection.getMarketValue())));
        containerColorID.setValue(selection.getContainerColor());
        fontColorID.setValue(selection.getFontColor());
        fillColorID.setValue(selection.getFillColor());
        if (selection.getName().equalsIgnoreCase("farm")) {
            posAtt.setText("N/A");
            sizeAtt.setText("(800, 600)");
            fontAtt.setText("N/A");
            containerAtt.setText("N/A");
            fillAtt.setText("N/A");
            int i = 0;
            totalFarmPrice = 0;
            totalFarmMarketValue = 0;
            while (i < treeView.getRoot().getChildren().size()) {
                totalFarmPrice = totalFarmPrice + treeView.getRoot().getChildren().get(i).getValue().getPriceAll();
                totalFarmMarketValue = totalFarmMarketValue + treeView.getRoot().getChildren().get(i).getValue().getMarketValueAll();
                i++;
            }

            selection.setPriceAll(totalFarmPrice + treeView.getRoot().getValue().getPrice());
            selection.setMarketValueAll(totalFarmMarketValue + treeView.getRoot().getValue().getMarketValue());

        } else {
            posAtt.setText("(" + df.format(selection.getPosX()) + ", " + df.format(selection.getPosY()) + ")");
            sizeAtt.setText("(" + df.format(selection.getLength()) + ", " + df.format(selection.getWidth()) + ")");
            fontAtt.setText(String.valueOf(selection.getFontColor()));
            containerAtt.setText(String.valueOf(selection.getContainerColor()));
            fillAtt.setText(String.valueOf(selection.getFillColor()));
        }
        if (selection.getChildren() == null && !selection.getName().equalsIgnoreCase("farm")) {
            marketValueAllAtt.setText("N/A");
            priceAllAtt.setText("N/A");
        } else {
            priceAllAtt.setText(String.valueOf(df.format(selection.getPriceAll())));
            marketValueAllAtt.setText(String.valueOf(df.format(selection.getMarketValueAll())));
        }
        nameAtt.setText(selection.getName());
        priceAtt.setText(String.valueOf(df.format(selection.getPrice())));
        marketValueAtt.setText(String.valueOf(df.format(selection.getMarketValue())));
    }

    ItemInfo getSelectedItem() { //Gets info for selected item
        TreeItem<ItemInfo> itemSelected = treeView.getSelectionModel().getSelectedItem();
        if (itemSelected == null) {
            return null;
        }
        return itemSelected.getValue();
    }

    ItemInfo getSelectedItemParent() { //Gets parent of item selected
        TreeItem<ItemInfo> itemSelected = treeView.getSelectionModel().getSelectedItem();
        if (itemSelected.getParent() == null) {
            return null;
        }
        return itemSelected.getParent().getValue();
    }

    ItemInfo getSelectedItemParentOfParent() { //Gets parent of item selected
        TreeItem<ItemInfo> itemSelected = treeView.getSelectionModel().getSelectedItem();
        TreeItem<ItemInfo> parent = itemSelected.getParent();
        if (parent == null || parent.getParent() == null) {
            return null;
        }
        return parent.getParent().getValue();
    }

    @FXML
    private void treeViewClick(MouseEvent event) { /*When the tree view is clicked the function determines if it is a
        left or right click then proceeds to set the correct menus, buttons, and panes according to the selected item*/
        ItemInfo selection = getSelectedItem();
        treeView.setOnKeyPressed(keyEvent -> {
            if(selection != null && !selection.getName().equalsIgnoreCase("farm") && selection.getDrone() != 1){
                if(keyEvent.getCode() == KeyCode.DELETE){
                    deleteMenu();
                }
            }
        });
        if (event.isPrimaryButtonDown()) {
            setValues();
            namePane.setVisible(false);
            positionPane.setVisible(false);
            sizePane.setVisible(false);
            valuePane.setVisible(false);
            colorPane.setVisible(false);
            marketValuePane.setVisible(false);
            startButton.setDisable(true);
            simulationRadioButton.setDisable(true);
            droneRadioButton.setDisable(true);
            attributesPane.setVisible(true);
            if (droneCount == 1) {
                startButton.setDisable(false);
                simulationRadioButton.setDisable(false);
                droneRadioButton.setDisable(false);
                if (getSelectedItem() == null) {
                    return;
                } else if (getSelectedItemIndex() == 0) {
                    startButton.setText("Start");
                } else if (getSelectedItem().hasNext() && getSelectedItem().getCommandCenter() == 1) {
                    startButton.setText("Scan Farm");
                } else if (getSelectedItem().getDrone() == 1) {
                    startButton.setDisable(true);
                    startButton.setText("Start");
                } else {
                    startButton.setText("Visit " + getSelectedItem().getName());
                }
            }
        }
        if (event.isSecondaryButtonDown()) {
            setValues();
            if (getSelectedItem() == null) {
                return;
            }
            startButton.setDisable(true);
            simulationRadioButton.setDisable(true);
            droneRadioButton.setDisable(true);
            namePane.setVisible(false);
            positionPane.setVisible(false);
            sizePane.setVisible(false);
            valuePane.setVisible(false);
            colorPane.setVisible(false);
            marketValuePane.setVisible(false);
            attributesPane.setVisible(true);
            if (getSelectedItemIndex() == 0) {
                addDrone.setDisable(false);
                addItem.setDisable(false);
                addContainer.setDisable(false);
                delete.setDisable(true);
                name.setDisable(true);
                position.setDisable(true);
                size.setDisable(true);
                price.setDisable(false);
                color.setDisable(true);
                marketValue.setDisable(false);

                addDrone.setVisible(true);
                addItem.setVisible(true);
                addContainer.setVisible(true);
                delete.setVisible(false);
                name.setVisible(false);
                position.setVisible(false);
                size.setVisible(false);
                price.setVisible(true);
                color.setVisible(false);
                marketValue.setVisible(true);

            } else if (getSelectedItem().hasNext() && getSelectedItem().getCommandCenter() == 1) {
                commandOrItem();

            } else if (getSelectedItem().getDrone() == 1) {
                addDrone.setDisable(true);
                addItem.setDisable(true);
                addContainer.setDisable(true);
                delete.setDisable(true);
                name.setDisable(true);
                position.setDisable(true);
                size.setDisable(true);
                price.setDisable(false);
                color.setDisable(true);
                marketValue.setDisable(false);

                addDrone.setVisible(false);
                addItem.setVisible(false);
                addContainer.setVisible(false);
                delete.setVisible(false);
                name.setVisible(false);
                position.setVisible(false);
                size.setVisible(false);
                price.setVisible(true);
                color.setVisible(false);
                marketValue.setVisible(true);

            } else if ((getSelectedItemParentOfParent() != null && getSelectedItemParentOfParent().getName().equalsIgnoreCase("farm")) && getSelectedItem().getChildren() != null) {
                addDrone.setDisable(true);
                addItem.setDisable(false);
                addContainer.setDisable(true);
                delete.setDisable(false);
                menuFunc1();
                menuFunc2();

            } else if (getSelectedItem().getChildren() != null) {
                addDrone.setDisable(true);
                addItem.setDisable(false);
                addContainer.setDisable(false);
                delete.setDisable(false);
                menuFunc1();
                addContainer.setVisible(true);
                delete.setVisible(true);
                name.setVisible(true);
                position.setVisible(true);
                size.setVisible(true);
                price.setVisible(true);
                color.setVisible(true);
                marketValue.setVisible(true);

            } else {
                commandOrItem();

            }
        }

    }
    private void menuFunc2() { //Repeated commands
        addContainer.setVisible(false);
        delete.setVisible(true);
        name.setVisible(true);
        position.setVisible(true);
        size.setVisible(true);
        price.setVisible(true);
        color.setVisible(true);
        marketValue.setVisible(true);
    }

    private void menuFunc1() { //Repeated commands
        name.setDisable(false);
        position.setDisable(false);
        size.setDisable(false);
        price.setDisable(false);
        color.setDisable(false);
        marketValue.setDisable(false);
        addDrone.setVisible(false);
        addItem.setVisible(true);
    }

    private void commandOrItem() { //Repeated commands
        addDrone.setDisable(true);
        addItem.setDisable(true);
        addContainer.setDisable(true);
        delete.setDisable(false);
        name.setDisable(false);
        position.setDisable(false);
        size.setDisable(false);
        price.setDisable(false);
        color.setDisable(false);
        marketValue.setDisable(false);
        addDrone.setVisible(false);
        addItem.setVisible(false);
        menuFunc2();
    }

    @FXML
    private void droneMenu() { //On menu click add drone and update list
        attributesPane.setVisible(false);
        drone.setDisable(false);
        drone.setVisible(true);
        if (droneCount > 0) {
            JOptionPane.showMessageDialog(null, "Only one Drone can be added",
                    "Drone Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        droneCount++;
        farmItemList.add(new ItemContainerCommandCenter("Command Center", 1, new ArrayList<>() {
            {
                add(new ItemLeafDrone("Drone", 1, 0, 0, 100, 100, 0, 0));
            }
        }, 0, 0, 0, 0, 50, 50, 100, 100, Color.BLACK, Color.BLACK, Color.WHITE));
        xPos = 100;
        yPos = 100;
        updateFarmItemList();
    }
    double[] menuVal(){ //Repeated commands
        attributesPane.setVisible(false);
        double[] val = new double[2];
        if (getSelectedItemIndex() == 0) {
            val[0] = 400;
            val[1] = 300;
        } else {
            val[0] = getSelectedItem().getPosX();
            val[1] = getSelectedItem().getPosY();
        }
        return val;
    }
    @FXML
    private void itemMenu() { //On menu click add item and update list
        double[] val;
        val = menuVal();
        int i = 0;
        ItemInfo selection = getSelectedItem();

        if(selection.getName().equalsIgnoreCase("farm")) {
            if (itemIndex == 0) {
                addItem(new ItemLeaf("New Item", 0, 0, val[0], val[1], 10, 10, Color.BLACK, Color.BLACK, Color.WHITE));
                itemIndex++;

            } else {
                while (i < farmItemList.size()) {
                    if (farmItemList.get(i).getName().equals("New Item")) {
                        itemIndex = 1;
                        itemCheck(1);
                        addItem(new ItemLeaf("New Item " + "(" + itemIndex + ")", 0, 0, val[0], val[1], 10, 10, Color.BLACK, Color.BLACK, Color.WHITE));
                        itemIndex++;
                        return;
                    }
                    i++;
                }
                addItem(new ItemLeaf("New Item", 0, 0, val[0], val[1], 10, 10, Color.BLACK, Color.BLACK, Color.WHITE));
            }
        }else{
            if (itemChildIndex == 0) {
                addItem(new ItemLeaf("New Item", 0, 0, val[0], val[1], 10, 10, Color.BLACK, Color.BLACK, Color.WHITE));
                itemChildIndex++;

            } else {
                while (i < selection.getChildren().size()) {
                    if (selection.getChildren().get(i).getName().equals("New Item")) {
                        itemChildIndex = 1;
                        itemChildCheck(selection.getChildren(), 1);
                        addItem(new ItemLeaf("New Item " + "(" + itemChildIndex + ")", 0, 0, val[0], val[1], 10, 10, Color.BLACK, Color.BLACK, Color.WHITE));
                        itemChildIndex++;
                        return;
                    }
                    i++;
                }
                addItem(new ItemLeaf("New Item", 0, 0, val[0], val[1], 10, 10, Color.BLACK, Color.BLACK, Color.WHITE));
            }
        }
    }
    private void itemCheck(int j){ //checks if item name already exists and creates the lowest index
        int i = 0;
        while (i < farmItemList.size()){
            if (farmItemList.get(i).getName().equals("New Item (" + j + ")")){
                j++;
                if (itemIndex < j){
                    itemIndex = j;
                }
                itemCheck(j);
            }
            i++;
        }
    }
    private void itemChildCheck(ArrayList<ItemInfo> list, int j){ //checks if item name already exists and creates the lowest index
        int i = 0;
        while (i < list.size()){
            if (list.get(i).getName().equals("New Item (" + j + ")")){
                j++;
                if (itemChildIndex < j){
                    itemChildIndex = j;
                }
                itemChildCheck(list, j);
            }
            i++;
        }
    }

    @FXML
    private void containerMenu() { //On menu click add container and update list
        double[] val;
        val = menuVal();
        int i = 0;
        ItemInfo selection = getSelectedItem();
        if(selection.getName().equalsIgnoreCase("farm")) {
            if (containerIndex == 0) {
                addItem(new ItemContainer("New Container", new ArrayList<>(), 0, 0, 0, 0, val[0], val[1], 75, 75, Color.BLACK, Color.BLACK, Color.WHITE));
                containerIndex++;
            } else {
                while (i < farmItemList.size()) {
                    if (farmItemList.get(i).getName().equals("New Container")) {
                        containerIndex = 1;
                        containerCheck(farmItemList, 1);
                        addItem(new ItemContainer("New Container " + "(" + containerIndex + ")", new ArrayList<>(), 0, 0, 0, 0, val[0], val[1], 75, 75, Color.BLACK, Color.BLACK, Color.WHITE));
                        containerIndex++;
                        return;
                    }
                    i++;
                }
                addItem(new ItemContainer("New Container", new ArrayList<>(), 0, 0, 0, 0, val[0], val[1], 75, 75, Color.BLACK, Color.BLACK, Color.WHITE));
            }
        }else {
            if (containerChildIndex == 0) {
                addItem(new ItemContainer("New Container", new ArrayList<>(), 0, 0, 0, 0, val[0], val[1], 75, 75, Color.BLACK, Color.BLACK, Color.WHITE));
                containerChildIndex++;
            } else {
                while (i < selection.getChildren().size()) {
                    if (selection.getChildren().get(i).getName().equals("New Container")) {
                        containerChildIndex = 1;
                        containerChildCheck(selection.getChildren(), 1);
                        addItem(new ItemContainer("New Container " + "(" + containerChildIndex + ")", new ArrayList<>(), 0, 0, 0, 0, val[0], val[1], 75, 75, Color.BLACK, Color.BLACK, Color.WHITE));
                        containerChildIndex++;
                        return;
                    }
                    i++;
                }
                addItem(new ItemContainer("New Container", new ArrayList<>(), 0, 0, 0, 0, val[0], val[1], 75, 75, Color.BLACK, Color.BLACK, Color.WHITE));
            }
        }
    }
    private void containerCheck(ArrayList<ItemInfo> list, int j){ //checks if container name already exists and creates the lowest index
        int i = 0;
        while (i < list.size()){
            if (list.get(i).getName().equals("New Container (" + j + ")")){
                j++;
                if (containerIndex < j){
                    containerIndex = j;
                }
                containerCheck(list, j);
            }
            i++;
        }
    }
    private void containerChildCheck(ArrayList<ItemInfo> list, int j){ //checks if container name already exists and creates the lowest index
        int i = 0;
        while (i < list.size()){
            if (list.get(i).getName().equals("New Container (" + j + ")")){
                j++;
                if (containerChildIndex < j){
                    containerChildIndex = j;
                }
                containerChildCheck(list, j);
            }
            i++;
        }
    }

    @FXML
    private void deleteMenu() { //On menu click delete selected item or container and update list
        attributesPane.setVisible(false);
        ItemInfo deleteItem = getSelectedItem();
        ArrayList<ItemInfo> child = getSelectedItem().getChildren();
        ItemInfo parent = getSelectedItemParent();
        if (parent == null){
            return;
        }
        ItemInfo parentOfParent = getSelectedItemParentOfParent();
        double totalValueOfChild = getSelectedItem().getPrice();
        double deleteAmountPrice = deleteItem.getPriceAll();
        double totalMarketValueOfChild = getSelectedItem().getMarketValue();
        double deleteAmountMarketValue= deleteItem.getMarketValueAll();
        if (deleteItem.getCommandCenter() == 1) {
            droneCount--;
        }
        int i = 0;
        int j = 0;
        if (child != null) {
            while (i < child.size()) {
                if (child.get(i).getChildren() != null) {
                    ArrayList<ItemInfo> childOfChild = child.get(i).getChildren();
                    while (j < childOfChild.size()) {
                        totalValueOfChild = totalValueOfChild + childOfChild.get(j).getPrice();
                        totalMarketValueOfChild = totalMarketValueOfChild + childOfChild.get(j).getMarketValue();
                        j++;
                    }
                }
                totalValueOfChild = totalValueOfChild + child.get(i).getPrice();
                totalMarketValueOfChild = totalMarketValueOfChild + child.get(i).getMarketValue();
                i++;
            }
            deleteAmountPrice = totalValueOfChild;
            deleteAmountMarketValue = totalMarketValueOfChild;
        }
        if (parent != null) {
            parent.setPriceAll(parent.getPriceAll() - deleteAmountPrice);
            parent.setMarketValueAll(parent.getMarketValueAll() - deleteAmountMarketValue);
        }
        if (parentOfParent != null && !parentOfParent.getName().equalsIgnoreCase("farm")) {
            parentOfParent.setPriceAll(parentOfParent.getPriceAll() - deleteAmountPrice);
            parentOfParent.setMarketValueAll(parentOfParent.getMarketValueAll() - deleteAmountMarketValue);
        }

        TreeItem<ItemInfo> item = treeView.getSelectionModel().getSelectedItem();
        TreeItem<ItemInfo> parent2 = item.getParent();

        if (String.valueOf(parent2.getValue()).equalsIgnoreCase("farm")) {
            farmItemList.remove(deleteItem);
        } else {
            parent2.getValue().removeChild(deleteItem);
        }
        updateFarmItemList();
        setValues();
    }

    @FXML
    private void nameMenu() { //Shows Menu
        attributesPane.setVisible(false);
        namePane.setVisible(true);
        nameField.requestFocus();
    }

    @FXML
    private void positionMenu() { //Shows Menu
        attributesPane.setVisible(false);
        positionPane.setVisible(true);
        posXField.requestFocus();
    }

    @FXML
    private void sizeMenu() { //Shows Menu
        attributesPane.setVisible(false);
        sizePane.setVisible(true);
        lengthField.requestFocus();
    }

    @FXML
    private void priceMenu() { //Shows Menu
        attributesPane.setVisible(false);
        valuePane.setVisible(true);
        priceField.requestFocus();
    }

    @FXML
    private void colorMenu() { //Shows Menu
        attributesPane.setVisible(false);
        colorPane.setVisible(true);
    }

    @FXML
    private void marketValueMenu() { //Shows Menu
        attributesPane.setVisible(false);
        marketValuePane.setVisible(true);
        marketValueField.requestFocus();
    }

    int getSelectedItemIndex() { //Gets item index
        return treeView.getSelectionModel().getSelectedIndex();
    }

    private void addItem(ItemInfo newItem) { //Adds item
        ItemInfo selection = getSelectedItem();
        if (selection != null && selection.getChildren() != null) {
            selection.addChild(newItem);
        } else {
            farmItemList.add(newItem);
        }
        updateFarmItemList();
    }

    private void updateFarmItemList() { //Updates farmlist
        treeView.getRoot().getChildren().clear();
        farmVisualization.getChildren().clear();
        farmItemList.forEach(itemInfo -> {
            addChildren(itemInfo, treeView.getRoot());
            drawTreeItems(itemInfo, treeView.getRoot());
        });
    }

    void addChildren(ItemInfo item, TreeItem<ItemInfo> parent) { //Adds children
        TreeItem<ItemInfo> newItem = new TreeItem<>(item);
        parent.getChildren().add(newItem);
        while (item.hasNext()) {
            addChildren(item.next(), newItem);
        }
    }

    @FXML
    private void nameSave() { //Saves name and updates farm list
        ItemInfo selection = getSelectedItem();
        if (valueCheck(nameField.getText())){
            return;
        }
        if (nameField.getText().equalsIgnoreCase( "farm")) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n There can only be one farm.",
                    "InfoBox: Name Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (selection.getName().equalsIgnoreCase("New Item(" + (itemIndex - 1) + ")")){
            itemIndex--;
        } else if (selection.getName().equalsIgnoreCase("New Container(" + (containerIndex - 1) + ")")) {
            containerIndex--;
        }
        selection.setName(nameField.getText());
        namePane.setVisible(false);
        setValues();

        updateFarmItemList();
    }
    @FXML
    private void nameKey(){ //Enter key calls save function
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                nameSave();
            }
        });
    }

    @FXML
    private void positionSave() { //Saves position and updates farm list
        ItemInfo selection = getSelectedItem();
        ItemInfo parent = getSelectedItemParent();
        ArrayList<ItemInfo> child = selection.getChildren();
        int i = 0;
        int j = 0;
        if (valueCheck(posXField.getText()) || numberCheck(posYField.getText())){
            return;
        }
        if(numberCheck(posXField.getText()) || numberCheck(posYField.getText())){
            return;
        }
        if(minMaxCheck(selection)){
            return;
        }
        double lastPosX = selection.getPosX();
        double lastPosY = selection.getPosY();
        double differenceX;
        double differenceY;
        if (lastPosX == Double.parseDouble(posXField.getText())) {
            differenceX = 0;
        } else {
            differenceX = Double.parseDouble(posXField.getText()) - lastPosX;
        }
        if (lastPosY == Double.parseDouble(posYField.getText())) {
            differenceY = 0;
        } else {
            differenceY = Double.parseDouble(posYField.getText()) - lastPosY;
        }
        if (selection.getCommandCenter() == 1){
            droneXPos = selection.getPosX() + differenceX + 50;
            droneYPos = selection.getPosY() + differenceY + 50;
        }

        if (child != null) {
            while (i < child.size()) {
                if (child.get(i).getChildren() != null) {
                    ArrayList<ItemInfo> childOfChild = child.get(i).getChildren();
                    while (j < childOfChild.size()) {
                        childOfChild.get(j).setPos(childOfChild.get(j).getPosX() + differenceX, childOfChild.get(j).getPosY() + differenceY);
                        j++;
                    }
                }
                child.get(i).setPos(child.get(i).getPosX() + differenceX, child.get(i).getPosY() + differenceY);
                i++;
            }
        }
        if (!parent.getName().equalsIgnoreCase("farm")) {
            if (parentPositionError(parent, selection)) {
                return;
            }
        }

        selection.setPos(Double.parseDouble(posXField.getText()), Double.parseDouble(posYField.getText()));
        positionPane.setVisible(false);
        setValues();
        updateFarmItemList();
    }
    @FXML
    private void positionKey(){ //Enter key calls save function
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                positionSave();
            }
        });
    }

    @FXML
    private void sizeSave() { //Saves size and updates farm list
        ItemInfo selection = getSelectedItem();
        ItemInfo parent = getSelectedItemParent();
        ArrayList<ItemInfo> child = selection.getChildren();
        if (valueCheck(lengthField.getText()) || numberCheck(widthField.getText())){
            return;
        }
        if(numberCheck(lengthField.getText()) || numberCheck(widthField.getText())){
            return;
        }
        if(sizeCheck(selection)){
            return;
        }

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
        }
        if (!parent.getName().equalsIgnoreCase("farm")) {
            if (parentSizeError(parent, selection)) {
                return;
            }
            if (childrenSizeCheck(childLengthMax, childWidthMax, parent)) {
                return;
            }
        }
        selection.setDimension(Double.parseDouble(lengthField.getText()), Double.parseDouble(widthField.getText()));
        sizePane.setVisible(false);
        setValues();
        updateFarmItemList();
    }
    @FXML
    private void sizeKey(){ //Enter key calls save function
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                sizeSave();
            }
        });
    }

    @FXML
    private void priceSave() { //Saves value and updates farm list
        ItemInfo selection = getSelectedItem();
        int index = getSelectedItemIndex();
        if (valueCheck(priceField.getText())){
            return;
        }
        if(numberCheck(priceField.getText())){
            return;
        }
        selection.setPrice(Double.parseDouble(priceField.getText()));
        selection.setPriceAll(Double.parseDouble(priceField.getText()));
        valuePane.setVisible(false);
        if (index != 0) {
            ItemInfo parent = getSelectedItemParent();
            ItemInfo parentOfParent = getSelectedItemParentOfParent();
            Values value = new Price();
            value.accept(new valuesDisplayVisitor(), selection, parent, parentOfParent);
        } else {
            totalFarmPrice = totalFarmPrice + Double.parseDouble(priceField.getText());
        }
        setValues();
        updateFarmItemList();

    }
    @FXML
    private void priceKey(){ //Enter key calls save function
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                priceSave();
            }
        });
    }

    @FXML
    private void marketValueSave() { //Saves value and updates farm list
        ItemInfo selection = getSelectedItem();
        int index = getSelectedItemIndex();
        if (valueCheck(marketValueField.getText())){
            return;
        }
        if(numberCheck(marketValueField.getText())){
            return;
        }
        selection.setMarketValue(Double.parseDouble(marketValueField.getText()));
        selection.setMarketValueAll(Double.parseDouble(marketValueField.getText()));
        marketValuePane.setVisible(false);
        if (index != 0) {
            ItemInfo parent = getSelectedItemParent();
            ItemInfo parentOfParent = getSelectedItemParentOfParent();
            Values value = new MarketValue();
            value.accept(new valuesDisplayVisitor(), selection, parent, parentOfParent);
        } else {
            totalFarmMarketValue = totalFarmMarketValue + Double.parseDouble(marketValueField.getText());
        }
        setValues();
        updateFarmItemList();
    }
    @FXML
    private void marketValueKey(){ //Enter key calls save function
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                marketValueSave();
            }
        });
    }

    @FXML
    private void colorSave() { //Saves color and updates farm list
        ItemInfo selection = getSelectedItem();
        selection.setFontColor(fontColorID.getValue());
        selection.setContainerColor(containerColorID.getValue());
        selection.setFillColor(fillColorID.getValue());
        colorPane.setVisible(false);
        setValues();
        updateFarmItemList();
    }
    @FXML
    private void colorKey(){ //Enter key calls save function
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                colorSave();
            }
        });
    }
    boolean valueCheck(String x){ //Checks for empty inputs
        if (x.isEmpty()){
            JOptionPane.showMessageDialog(null, "Value Entered is not valid.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }
    boolean numberCheck(String x){ //checks value is a number
        try{
            Double.parseDouble(x);
            if (Double.parseDouble(x) < 0){
                JOptionPane.showMessageDialog(null, "Value Entered is not valid.",
                        "Value Error", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
            return false;
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Value Entered is not valid.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
    }
    boolean minMaxCheck(ItemInfo selection) { //Checks to make sure the object is within the bounds of the farm
        if (Double.parseDouble(posXField.getText()) + selection.getLength() <= 0) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(posYField.getText()) + selection.getWidth() <= 0) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(posXField.getText()) + selection.getLength() > 800) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(posYField.getText()) + selection.getWidth() > 600) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    boolean sizeCheck(ItemInfo selection) { //Checks to make sure the size of the object is within the bounds of the farm
        if (Double.parseDouble(lengthField.getText()) + selection.getPosX() <= 0) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(widthField.getText()) + selection.getPosY() <= 0) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(lengthField.getText()) + selection.getPosX() > 800) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(widthField.getText()) + selection.getPosY() > 600) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    boolean parentPositionError(ItemInfo parent, ItemInfo selection) { //Checks to make sure the object is within the bounds of its container
        if (Double.parseDouble(posXField.getText()) < parent.getPosX()) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(posYField.getText()) < parent.getPosY()) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(posXField.getText()) + selection.getLength() > parent.getPosX() + parent.getLength()) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(posYField.getText()) + selection.getWidth() > parent.getPosY() + parent.getWidth()) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    boolean parentSizeError(ItemInfo parent, ItemInfo selection) { //Checks to make sure the objects size is within the bounds of its container
        if (Double.parseDouble(lengthField.getText()) + selection.getPosX() >= parent.getPosX() + parent.getLength()) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

        if (Double.parseDouble(widthField.getText()) + selection.getPosY() >= parent.getPosY() + parent.getWidth()) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    boolean childrenSizeCheck(double childLengthMax, double childWidthMax, ItemInfo parent) { //Checks to make sure the parent does not become smaller than its items
        if (Double.parseDouble(lengthField.getText()) + parent.getPosX() < childLengthMax) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

        if (Double.parseDouble(widthField.getText()) + parent.getPosY() < childWidthMax) {
            JOptionPane.showMessageDialog(null, "Values are out of bounds.",
                    "Value Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    private void drawItem(ItemInfo item) { //Draws item in map
        double red = item.getFillColor().getRed();
        double green = item.getFillColor().getGreen();
        double blue = item.getFillColor().getBlue();
        Color rgba = new Color(red, green, blue, 0.75);
        Rectangle rect = new Rectangle(item.getPosX(), item.getPosY(), item.getLength(), item.getWidth());
        rect.setAccessibleText(item.getName());
        rect.setStroke(item.getContainerColor());
        rect.setFill(rgba);
        rect.setStrokeWidth(2);
        farmVisualization.getChildren().add(rect);
    }

    private void drawItemContainer(ItemInfo itemContainer) { //Draws container in map
        double red = itemContainer.getFillColor().getRed();
        double green = itemContainer.getFillColor().getGreen();
        double blue = itemContainer.getFillColor().getBlue();
        Color rgba = new Color(red, green, blue, 0.5);
        Rectangle rect = new Rectangle(itemContainer.getPosX(), itemContainer.getPosY(), itemContainer.getLength(), itemContainer.getWidth());
        rect.setAccessibleText(itemContainer.getName());
        rect.setStroke(itemContainer.getContainerColor());
        rect.setFill(rgba);
        rect.setStrokeWidth(5);
        farmVisualization.getChildren().add(rect);
    }

    void drawTreeItems(ItemInfo item, TreeItem<ItemInfo> parent) { //Draws all tree items
        TreeItem<ItemInfo> newTreeItem = new TreeItem<>(item);
        Text text = new Text(item.getPosX(), item.getPosY() - 5, item.getName());
        text.setAccessibleText(item.getName());
        text.setFill(item.getFontColor());
        if (item.getChildren() != null) {
            farmVisualization.getChildren().add(text);
            drawItemContainer(item);
        } else {
            if (item.getName().equals("Drone")) {
                drone.setX(item.getPosX() - 50);
                drone.setY(item.getPosY() - 50);
                farmVisualization.getChildren().add(drone);
            } else {
                farmVisualization.getChildren().add(text);
                drawItem(item);
            }
        }
        while (item.hasNext()) {
            drawTreeItems(item.next(), newTreeItem);
        }
    }

    @FXML
    private void onClickSimulationRadioButton() { //changes radio buttons
        radioButton(droneRadioButton, simulationRadioButton);
    }
    @FXML
    private void onClickDroneRadioButton() { //changes radio buttons
        radioButton(simulationRadioButton, droneRadioButton);
    }
    private void radioButton(RadioButton droneRadioButton, RadioButton simulationRadioButton) { //changes radio buttons
        if (droneRadioButton.isSelected()) {
            droneRadioButton.setSelected(false);
        } else {
            simulationRadioButton.setSelected(true);
        }
        ItemInfo selected = getSelectedItem();
        if (selected.getCommandCenter() == 1){
            startButton.setText("Scan Farm");
        } else{
            startButton.setText("Visit: " + selected.getName());
        }
    }
    @FXML
    private void onClickStartButton() throws IOException, InterruptedException { //launches drone or simulation
        String test;
        ItemInfo selection = getSelectedItem();
        Simulation simulation = new Simulation();
        if (simulationRadioButton.isSelected()){
            test = "notReal";
            if (selection.getCommandCenter() == 1){
                simulation.scanFarm(selection, test);
            }else {
                simulation.visitItem(selection, test);
            }
        } else if (droneRadioButton.isSelected()) {
            test = "real";
            if (selection.getCommandCenter() == 1){
                simulation.scanFarm(selection, test);
            }else {
                simulation.visitItem(selection, test);
            }
        }
    }
}
