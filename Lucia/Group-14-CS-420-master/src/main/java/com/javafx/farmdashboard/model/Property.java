package com.javafx.farmdashboard.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Property {
    private StringProperty name;
    public void setName(String value) { nameProperty().set(value); }
    public String getName() { return nameProperty().get(); }
    public StringProperty nameProperty() {
        if (name == null) name = new SimpleStringProperty(this, "name");
        return name;
    }

    private StringProperty value;
    public void setValue(String value) { valueProperty().set(value); }
    public String getValue() { return valueProperty().get(); }
    public StringProperty valueProperty() {
        if (value == null) value = new SimpleStringProperty(this, "value");
        return value;
    }

    public Property(String name, String value) {
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleStringProperty(value);
    }
}
