package com.example.designandimplementation;

import javafx.fxml.FXML;

import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;


import items.*;

public class Controller{
    private ArrayList<ItemInfo> farmItemList;
    @FXML
    private TreeView<ItemInfo> treeView;

    private static Controller INSTANCE;
    public static Controller getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new Controller();
        }
        return INSTANCE;

    }

    @FXML
    public void initialize() {
        getInstance();
        farmItemList = new ArrayList<ItemInfo>();
        ItemLeaf farm = new ItemLeaf("Farm");
        TreeItem<ItemInfo> rootItem = new TreeItem<ItemInfo>(farm);
        rootItem.setExpanded(true);
        treeView.setRoot(rootItem);
    }
    ItemInfo getSelectedItem(){
        TreeItem<ItemInfo> itemSelected = (TreeItem<ItemInfo>) treeView.getSelectionModel().getSelectedItem();
        if (itemSelected == null){
            return null;
        }
        return itemSelected.getValue();
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
        farmItemList.forEach(itemInfo -> {
            addChildren(itemInfo, treeView.getRoot());
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
    private void itemContainerClick(){
        addItem(new ItemContainer("New_ItemContainer", new ArrayList<ItemInfo>()));
    }
    @FXML
    private void itemClick(){
        addItem(new ItemLeaf("New_Item"));
    }

    @FXML
    private void deleteItemClick(){
        ItemInfo deleteItem = getSelectedItem();
        if (deleteItem == null || String.valueOf(deleteItem) == "Farm") return;

        TreeItem<ItemInfo> item = treeView.getSelectionModel().getSelectedItem();
        TreeItem<ItemInfo> parent = item.getParent();
        if (String.valueOf(parent.getValue()) == "Farm"){
            farmItemList.remove(deleteItem);
        }else {
            parent.getValue().removeChild(deleteItem);
        }
        updateFarmItemList();
    }
}