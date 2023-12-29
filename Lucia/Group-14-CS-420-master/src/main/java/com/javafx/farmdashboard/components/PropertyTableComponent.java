package com.javafx.farmdashboard.components;

import com.javafx.farmdashboard.model.Item;
import com.javafx.farmdashboard.model.ItemI;
import com.javafx.farmdashboard.model.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;


public class PropertyTableComponent {

    private TableView<Property> propertyTableView;

    private TableColumn<Property, StringProperty> propertyNameColumn;

    private TableColumn<Property, StringProperty> propertyValueColumn;

    public PropertyTableComponent(TableView<Property> tableView, TableColumn<Property, StringProperty> column1, TableColumn<Property, StringProperty> column2) {
        propertyTableView = tableView;
        propertyNameColumn = column1;
        propertyValueColumn = column2;

        initialize();
    }

    public void initialize() {
        propertyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        propertyValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    public void displayProperties(ItemI item) {
        ObservableList<Property> propertyRows = FXCollections.observableArrayList();
        propertyRows.addAll(createPropertyRows(item));
        propertyTableView.setItems(propertyRows);
    }

    public List<Property> createPropertyRows(ItemI item) {

        Property name = new Property("Name", item.getName());
        Property x = new Property("X", String.format("%.2f", item.getPosX()));
        Property y = new Property("Y", String.format("%.2f", item.getPosY()));
        Property length = new Property("Length", String.format("%.2f", item.getLength()));
        Property width = new Property("Width", String.format("%.2f", item.getWidth()));
        Property price = new Property("Price", String.valueOf(item.getPrice()));
        Property marketValue = new Property("Market Value", String.valueOf(item.getMarketValue()));

        return item.isContainer() ? List.of(name, x, y, length, width, price) : List.of(name, x, y, length, width, price, marketValue);
    }

}
