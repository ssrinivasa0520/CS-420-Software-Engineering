package com.example.designandimplementation;

import javax.swing.*;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;
import javafx.scene.text.Text;
import items.*;
import static java.lang.Math.round;

public class Controller {
    public static int droneCount = 0; //Keeps drone count only one drone allowed
    private ArrayList<ItemInfo> farmItemList; //List of items on farm
    @FXML
    private TreeView<ItemInfo> treeView; // Treeview with iteminfo
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
    public RadioButton visitRadioButton;
    @FXML
    public RadioButton scanRadioButton;
    @FXML
    //Drone Command Buttons
    public Button launchButton;
    @FXML
    public Button simulatorButton;
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
    //Position of objects on map
    public static double xPos;
    public static double yPos;
    private DecimalFormat df = new DecimalFormat("0.00");
    public static double totalFarmPrice = 0;
    public static double totalFarmMarketValue = 0;


    @FXML
    public void initialize() { //Initializes the tree view and updates it on change
        Singleton.getInstance();
        farmItemList = new ArrayList<ItemInfo>();
        ItemLeaf farm = new ItemLeaf("Farm", 0, 0, 0, 0, 0, 0, Color.BLACK, Color.BLACK, Color.WHITE);
        TreeItem<ItemInfo> rootItem = new TreeItem<ItemInfo>(farm);
        rootItem.setExpanded(true);
        treeView.setRoot(rootItem);
        treeView.getSelectionModel().selectedItemProperty().addListener((ChangeListener)
                (observable, oldValue, newValue) -> {
                    setValues();
                }); // saves values
    }

    void setValues() { //Sets current values to fields and attributes
        if (getSelectedItem() == null) {
            return;
        }
        ItemInfo selection = getSelectedItem();
        int index = getSelectedItemIndex();
        nameField.setText(selection.getName());
        posXField.setText(String.valueOf(selection.getPosX()));
        posYField.setText(String.valueOf(selection.getPosY()));
        lengthField.setText(String.valueOf(selection.getLength()));
        widthField.setText(String.valueOf(selection.getWidth()));
        priceField.setText(String.valueOf(df.format(selection.getPrice())));
        marketValueField.setText(String.valueOf(df.format(selection.getMarketValue())));
        containerColorID.setValue(selection.getContainerColor());
        fontColorID.setValue(selection.getFontColor());
        fillColorID.setValue(selection.getFillColor());
        if (index == 0) {
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
            posAtt.setText("(" + round(selection.getPosX()) + ", " + round(selection.getPosY()) + ")");
            sizeAtt.setText("(" + round(selection.getLength()) + ", " + round(selection.getWidth()) + ")");
            fontAtt.setText(String.valueOf(selection.getFontColor()));
            containerAtt.setText(String.valueOf(selection.getContainerColor()));
            fillAtt.setText("N/A");
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
        if (itemSelected == null) {
            return null;
        }
        return itemSelected.getParent().getValue();
    }

    ItemInfo getSelectedItemParentOfParent() { //Gets parent of item selected
        TreeItem<ItemInfo> itemSelected = treeView.getSelectionModel().getSelectedItem();
        TreeItem<ItemInfo> parent = itemSelected.getParent();
        if (parent == null || itemSelected == null || parent.getParent() == null) {
            return null;
        }
        return parent.getParent().getValue();
    }

    @FXML
    private void treeViewClick(MouseEvent event) { /*When the tree view is clicked the function determines if it is a
        left or right click then proceeds to set the correct menus, buttons, and panes according to the selected item*/
        if (event.isPrimaryButtonDown()) {
            namePane.setVisible(false);
            positionPane.setVisible(false);
            sizePane.setVisible(false);
            valuePane.setVisible(false);
            colorPane.setVisible(false);
            marketValuePane.setVisible(false);
            simulatorButton.setDisable(true);
            launchButton.setDisable(true);
            attributesPane.setVisible(true);
            if (visitRadioButton.isSelected()) {
                if (getSelectedItem() == null) {
                    return;
                } else if (getSelectedItemIndex() == 0) {
                    simulatorButton.setText("Visit " + getSelectedItem().getName());
                    launchButton.setText("Visit " + getSelectedItem().getName());
                } else if (getSelectedItem().hasNext() && getSelectedItem().getCommandCenter() == 1) {
                    simulatorButton.setDisable(false);
                    simulatorButton.setText("Visit " + getSelectedItem().getName());
                    launchButton.setDisable(false);
                    launchButton.setText("Visit " + getSelectedItem().getName());
                } else if (getSelectedItem().getDrone() == 1) {
                    simulatorButton.setText("Visit " + getSelectedItem().getName());
                    launchButton.setText("Visit " + getSelectedItem().getName());
                } else if (getSelectedItem().getChildren() != null) {
                    if (droneCount == 1) {
                        simulatorButton.setDisable(false);
                        launchButton.setDisable(false);
                    }
                    simulatorButton.setText("Visit " + getSelectedItem().getName());
                    launchButton.setText("Visit " + getSelectedItem().getName());
                } else {
                    if (droneCount == 1) {
                        simulatorButton.setDisable(false);
                        launchButton.setDisable(false);
                    }
                    simulatorButton.setText("Visit " + getSelectedItem().getName());
                    launchButton.setText("Visit " + getSelectedItem().getName());
                }
            }
            if (scanRadioButton.isSelected() && getSelectedItem().hasNext() && getSelectedItem().getCommandCenter() == 1) {
                simulatorButton.setDisable(false);
                simulatorButton.setText("Scan Farm");
                launchButton.setDisable(false);
                launchButton.setText("Scan Farm");
            }
        }
        if (event.isSecondaryButtonDown()) {
            if (getSelectedItem() == null) {
                return;
            }
            launchButton.setDisable(true);
            simulatorButton.setDisable(true);
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
                addContainer.setVisible(false);
                delete.setVisible(true);
                name.setVisible(true);
                position.setVisible(true);
                size.setVisible(true);
                price.setVisible(true);
                color.setVisible(true);
                marketValue.setVisible(true);

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
                name.setDisable(false);
                position.setDisable(false);
                size.setDisable(false);
                price.setDisable(false);
                color.setDisable(false);
                marketValue.setDisable(false);

                addDrone.setVisible(false);
                addItem.setVisible(true);
                addContainer.setVisible(false);
                delete.setVisible(true);
                name.setVisible(true);
                position.setVisible(true);
                size.setVisible(true);
                price.setVisible(true);
                color.setVisible(true);
                marketValue.setVisible(true);

            } else if (getSelectedItem().getChildren() != null) {
                addDrone.setDisable(true);
                addItem.setDisable(false);
                addContainer.setDisable(false);
                delete.setDisable(false);
                name.setDisable(false);
                position.setDisable(false);
                size.setDisable(false);
                price.setDisable(false);
                color.setDisable(false);
                marketValue.setDisable(false);

                addDrone.setVisible(false);
                addItem.setVisible(true);
                addContainer.setVisible(true);
                delete.setVisible(true);
                name.setVisible(true);
                position.setVisible(true);
                size.setVisible(true);
                price.setVisible(true);
                color.setVisible(true);
                marketValue.setVisible(true);

            } else {
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
                addContainer.setVisible(false);
                delete.setVisible(true);
                name.setVisible(true);
                position.setVisible(true);
                size.setVisible(true);
                price.setVisible(true);
                color.setVisible(true);
                marketValue.setVisible(true);

            }
        }

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
        farmItemList.add(new ItemContainerCommandCenter("Command Center", 1, new ArrayList<ItemInfo>() {
            {
                add(new ItemLeafDrone("Drone", 1, 0, 0, 100, 100, 0, 0));
            }
        }, 0, 0, 0, 0, 50, 50, 100, 100, Color.BLACK, Color.BLACK, Color.WHITE));
        xPos = 100;
        yPos = 100;
        updateFarmItemList();
    }

    @FXML
    private void itemMenu() { //On menu click add item and update list
        attributesPane.setVisible(false);
        double x;
        double y;
        if (getSelectedItemIndex() == 0) {
            x = 400;
            y = 300;
        } else {
            x = getSelectedItem().getPosX();
            y = getSelectedItem().getPosY();
        }
        addItem(new ItemLeaf("New_Item", 0, 0, x, y, 5, 5, Color.BLACK, Color.BLACK, Color.WHITE));
    }

    @FXML
    private void containerMenu() { //On menu click add container and update list
        attributesPane.setVisible(false);
        double x;
        double y;
        if (getSelectedItemIndex() == 0) {
            x = 400;
            y = 300;
        } else {
            x = getSelectedItem().getPosX();
            y = getSelectedItem().getPosY();
        }
        addItem(new ItemContainer("New_Container", new ArrayList<ItemInfo>(), 0, 0, 0, 0, x, y, 50, 50, Color.BLACK, Color.BLACK, Color.WHITE));
    }

    @FXML
    private void deleteMenu() { //On menu click delete selected item or container and update list
        attributesPane.setVisible(false);
        ItemInfo deleteItem = getSelectedItem();
        ArrayList<ItemInfo> child = getSelectedItem().getChildren();
        ItemInfo parent = getSelectedItemParent();
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
            System.out.println(deleteAmountPrice + " deleteAmount");
        }
        if (parent != null) {
            System.out.println(deleteAmountPrice + " deleteAmount2");
            System.out.println(parent.getPriceAll());
            parent.setPriceAll(parent.getPriceAll() - deleteAmountPrice);
            parent.setMarketValueAll(parent.getMarketValueAll() - deleteAmountMarketValue);
            System.out.println(parent.getPriceAll());
        }
        if (parentOfParent != null && !parentOfParent.getName().equalsIgnoreCase("farm")) {
            System.out.println(deleteAmountPrice + " deleteAmount3");
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
    }

    @FXML
    private void positionMenu() { //Shows Menu
        attributesPane.setVisible(false);
        positionPane.setVisible(true);
    }

    @FXML
    private void sizeMenu() { //Shows Menu
        attributesPane.setVisible(false);
        sizePane.setVisible(true);
    }

    @FXML
    private void priceMenu() { //Shows Menu
        attributesPane.setVisible(false);
        valuePane.setVisible(true);
    }

    @FXML
    private void colorMenu() { //Shows Menu
        attributesPane.setVisible(false);
        colorPane.setVisible(true);
    }

    @FXML
    private void marketValueMenu() {
        attributesPane.setVisible(false);
        marketValuePane.setVisible(true);
    }

    int getSelectedItemIndex() { //Shows Menu
        return treeView.getSelectionModel().getSelectedIndex();
    }

    private void addItem(ItemInfo newItem) { //Shows Menu
        ItemInfo selection = getSelectedItem();
        if (selection != null && selection.getChildren() != null) {
            selection.addChild(newItem);
        } else {
            farmItemList.add(newItem);
        }
        updateFarmItemList();

    }

    private void updateFarmItemList() { //Shows Menu
        treeView.getRoot().getChildren().clear();
        farmVisualization.getChildren().clear();
        farmItemList.forEach(itemInfo -> {
            addChildren(itemInfo, treeView.getRoot());
            drawTreeItems(itemInfo, treeView.getRoot());
        });
    }

    void addChildren(ItemInfo item, TreeItem<ItemInfo> parent) { //Adds children
        TreeItem<ItemInfo> newItem = new TreeItem<ItemInfo>(item);
        parent.getChildren().add(newItem);
        while (item.hasNext()) {
            addChildren(item.next(), newItem);

        }
    }

    @FXML
    private void nameSave() { //Saves name and updates farm list
        ItemInfo selection = getSelectedItem();
        int index = getSelectedItemIndex();
        if (Objects.equals(nameField.getText().toLowerCase(), "farm")) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n There can only be one farm.",
                    "InfoBox: Name Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        selection.setName(nameField.getText());
        namePane.setVisible(false);
        setValues();

        updateFarmItemList();
    }

    @FXML
    private void positionSave() { //Saves position and updates farm list
        ItemInfo selection = getSelectedItem();
        ItemInfo parent = getSelectedItemParent();
        ArrayList<ItemInfo> child = selection.getChildren();
        int i = 0;
        int j = 0;
        minMaxCheck(selection);
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

        if (child != null) {
            while (i < child.size()) {
                if (child.get(i).getChildren() != null) {
                    ArrayList<ItemInfo> childOfChild = child.get(i).getChildren();
                    System.out.println(childOfChild);
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
    private void sizeSave() { //Saves size and updates farm list
        ItemInfo selection = getSelectedItem();
        ItemInfo parent = getSelectedItemParent();
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
            if (!parent.getName().equalsIgnoreCase("farm")) {
                if (parentSizeError(parent, selection)) {
                    return;
                }
                if (childrenSizeCheck(childLengthMax, childWidthMax, parent)) {
                    return;
                }
            }
        }
        selection.setDimension(Double.parseDouble(lengthField.getText()), Double.parseDouble(widthField.getText()));
        sizePane.setVisible(false);
        setValues();

        updateFarmItemList();
    }

    @FXML
    private void priceSave() { //Saves value and updates farm list
        ItemInfo selection = getSelectedItem();
        int index = getSelectedItemIndex();
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
    private void marketValueSave() { //Saves value and updates farm list
        ItemInfo selection = getSelectedItem();
        int index = getSelectedItemIndex();
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
    private void colorSave() { //Saves color and updates farm list
        ItemInfo selection = getSelectedItem();
        selection.setFontColor(fontColorID.getValue());
        selection.setContainerColor(containerColorID.getValue());
        selection.setFillColor(fillColorID.getValue());
        colorPane.setVisible(false);
        setValues();

        updateFarmItemList();
    }

    void minMaxCheck(ItemInfo selection) { //Checks to make sure the object is within the bounds of the farm
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

    void sizeCheck(ItemInfo selection) { //Checks to make sure the size of the object is within the bounds of the farm
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

    boolean parentPositionError(ItemInfo parent, ItemInfo selection) { //Checks to make sure the object is within the bounds of its container
        if (Double.parseDouble(posXField.getText()) < parent.getPosX()) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value greater than the minimum of the container " + parent.getPosX(),
                    "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(posYField.getText()) < parent.getPosY()) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value greater than the minimum of the container " + parent.getPosY(),
                    "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(posXField.getText()) + selection.getLength() > parent.getPosX() + parent.getLength()) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please either change the item's size or enter a value lower than the maximum of the container " + ((parent.getPosX() + parent.getLength()) - selection.getLength()),
                    "InfoBox: X Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (Double.parseDouble(posYField.getText()) + selection.getWidth() > parent.getPosY() + parent.getWidth()) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please either change the item's size or enter a value lower than the maximum of the container " + ((parent.getPosY() + parent.getWidth()) - selection.getWidth()),
                    "InfoBox: Y Coordinate Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    boolean parentSizeError(ItemInfo parent, ItemInfo selection) { //Checks to make sure the objects size is within the bounds of its container
        System.out.println();
        if (Double.parseDouble(lengthField.getText()) + selection.getPosX() >= parent.getPosX() + parent.getLength()) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value less than " + (parent.getLength() - (selection.getPosX() - parent.getPosX())),
                    "InfoBox: Length Size Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

        if (Double.parseDouble(widthField.getText()) + selection.getPosY() >= parent.getPosY() + parent.getWidth()) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value less than " + (parent.getWidth() - (selection.getPosY() - parent.getPosY())),
                    "InfoBox: Width Size Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        return false;
    }

    boolean childrenSizeCheck(double childLengthMax, double childWidthMax, ItemInfo parent) { //Checks to make sure the parent does not become smaller than its items
        System.out.println(Double.parseDouble(lengthField.getText()) + parent.getPosX());
        if (Double.parseDouble(lengthField.getText()) + parent.getPosX() < childLengthMax) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value greater than " + (childLengthMax - parent.getPosX()),
                    "InfoBox: Length Size Error", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

        if (Double.parseDouble(widthField.getText()) + parent.getPosY() < childWidthMax) {
            JOptionPane.showMessageDialog(null, "Entered Values Not Saved\n Please enter a value greater than " + (childWidthMax - parent.getPosY()),
                    "InfoBox: Width Size Error", JOptionPane.INFORMATION_MESSAGE);
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
        TreeItem<ItemInfo> newTreeItem = new TreeItem<ItemInfo>(item);
        Text text = new Text(item.getPosX(), item.getPosY() - 5, item.getName());
        text.setAccessibleText(item.getName());
        text.setFill(item.getFontColor());
        if (item != null && item.getChildren() != null) {
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
    private void onClickVisitRadioButton() {
        if (scanRadioButton.isSelected()) {
            scanRadioButton.setSelected(false);
        } else {
            visitRadioButton.setSelected(true);
        }
        ItemInfo selected = getSelectedItem();
        if (selected != null) {
            simulatorButton.setText("Visit " + selected.getName());
            launchButton.setText("Visit " + selected.getName());
        }
        if (droneCount > 0) {
            if (selected.getName().equalsIgnoreCase("farm")) {
                simulatorButton.setDisable(true);
                launchButton.setDisable(true);
            } else {
                ItemInfo parent = getSelectedItemParent();
                simulatorButton.setDisable(parent.getCommandCenter() == 1);
                launchButton.setDisable(parent.getCommandCenter() == 1);
            }

        }
    }

    @FXML
    private void onClickScanRadioButton() {
        if (visitRadioButton.isSelected()) {
            visitRadioButton.setSelected(false);
        } else {
            scanRadioButton.setSelected(true);
        }
        simulatorButton.setText("Scan Farm");
        launchButton.setText("Scan Farm");
        ItemInfo selected = getSelectedItem();
        if (selected != null) {
            simulatorButton.setDisable(selected.getCommandCenter() != 1);
            launchButton.setDisable(selected.getCommandCenter() != 1);
        }


    }

    @FXML
    private void onClickSimulator() throws IOException, InterruptedException {
        ItemInfo selection = getSelectedItem();
        Simulation simulation = new Simulation();
        if (scanRadioButton.isSelected()) {
            simulation.scanFarm(selection, "notReal");
        }
        if (visitRadioButton.isSelected()) {
            simulation.visitItem(selection, "notReal");

        }
    }

    @FXML
    private void onClickLaunch() throws IOException, InterruptedException {
        ItemInfo selection = getSelectedItem();
        Simulation simulation = new Simulation();
        if (scanRadioButton.isSelected()) {
            simulation.scanFarm(selection, "real");
        }
        if (visitRadioButton.isSelected()) {
            simulation.visitItem(selection, "real");

        }
    }

    /*void visitItem() { //Gets path to item on farm and animates the drone to move along that path
        if (getSelectedItem().getLength() / 2 + getSelectedItem().getPosX() == xPos && getSelectedItem().getWidth() / 2 + getSelectedItem().getPosY() == yPos) {
            return;
        }
        Path scan = new Path();
        System.out.println(xPos);
        System.out.println(yPos);
        scan.getElements().add(new MoveTo(xPos, yPos));
        scan.getElements().add(new LineTo(getSelectedItem().getLength() / 2 + getSelectedItem().getPosX(), getSelectedItem().getWidth() / 2 + getSelectedItem().getPosY()));
        PathTransition scantransition = new PathTransition();
        System.out.println(drone);
        scantransition.setNode(drone);
        scantransition.setDuration(Duration.millis(3000));
        scantransition.setPath(scan);
        scantransition.setCycleCount(1);
        scantransition.play();
        xPos = getSelectedItem().getLength() / 2 + getSelectedItem().getPosX();
        yPos = getSelectedItem().getWidth() / 2 + getSelectedItem().getPosY();
        simulatorButton.setDisable(true);
    }

    void scanFarm() { //animates drone to scan the entirety of the farm
        int point = 600;
        if (droneCount == 0) {
            return;
        }
        Path scan = new Path();
        scan.getElements().add(new MoveTo(xPos, yPos));
        scan.getElements().add(new LineTo(25, 25));
        while (true) {
            point = point - 25;
            scan.getElements().add(new VLineTo(point));
            scan.getElements().add(new HLineTo(775));
            if (point == 25) {
                break;
            }
            point = point - 25;
            scan.getElements().add(new VLineTo(point));
            scan.getElements().add(new HLineTo(25));
        }
        scan.getElements().add(new VLineTo(getSelectedItem().getWidth() / 2 + getSelectedItem().getPosY()));
        scan.getElements().add(new HLineTo(getSelectedItem().getLength() / 2 + getSelectedItem().getPosX()));
        PathTransition scantransition = new PathTransition();
        scantransition.setNode(drone);
        scantransition.setDuration(Duration.millis(15000));
        scantransition.setPath(scan);
        scantransition.setCycleCount(1);
        scantransition.play();

        scantransition.setOnFinished(event -> {

        });
        xPos = getSelectedItem().getLength() / 2 + getSelectedItem().getPosX();
        yPos = getSelectedItem().getWidth() / 2 + getSelectedItem().getPosY();
    }*/
}
