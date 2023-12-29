package com.javafx.farmdashboard.model;

import com.javafx.farmdashboard.Constants;
import com.javafx.farmdashboard.helpers.VisitorI;

import java.util.List;

public interface ItemI {

    public boolean isContainer();

    public void setContainer(boolean container);

    public Constants.ItemType getType();

    public void setType(Constants.ItemType type);

    public ItemI getParent();

    public void setParent(ItemI parent);

    public List<ItemI> getChildren();

    public void setChildren(List<ItemI> children);

    public String getName();

    public void setName(String name);

    public Double getPosX();

    public void setPosX(Double posX);

    public Double getPosY();

    public void setPosY(Double posY);

    public Double getPrice();

    public void setPrice(Double price);

    public Double getLength();

    public void setLength(Double length);

    public Double getWidth();

    public void setWidth(Double width);

    public Double getMarketValue();

    public void setMarketValue(Double marketValue);

    public boolean isDefault();

    public boolean isFarm();

    public boolean isCommandCenter();

    public boolean isDrone();

    public double accept(VisitorI visitor);

}
