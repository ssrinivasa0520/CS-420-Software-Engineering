package items;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public interface ItemInfo {
    //Setters
    void setName (String name);
    void setPrice(double price);
    void setMarketValue(double value);
    void setPriceAll(double priceAll);
    void setMarketValueAll(double valueAll);
    void setPos (double x, double y);
    void setDimension (double length, double width);
    void setFontColor (Color fontColor);
    void setContainerColor (Color containerColor);

    //Getters
    String getName();
    double getPrice();
    double getPriceAll();
    double getMarketValue();
    double getMarketValueAll();
    double getPosX();
    double getPosY();
    double getLength();
    double getWidth();
    int getCommandCenter();
    int getDrone();
    Color getFontColor();
    Color getContainerColor();
    ArrayList<ItemInfo> getChildren();

    //Functions
    void addChild(ItemInfo item);
    void removeChild(ItemInfo item);
    ItemInfo next();
    boolean hasNext();
}