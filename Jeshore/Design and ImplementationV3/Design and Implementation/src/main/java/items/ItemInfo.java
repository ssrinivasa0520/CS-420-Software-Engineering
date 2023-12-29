package items;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public interface ItemInfo {
    //Set
    void setName (String name);
    void setValue (double price);
    void setPos (double x, double y);
    void setDimension (double length, double width);
    void setFontColor (Color fontColor);
    void setContainerColor (Color containerColor);

    //Get
    String getName();
    double getValue();
    double getPosX();
    double getPosY();
    double getLength();
    double getWidth();
    int getCommandCenter();
    int getDrone();
    Color getFontColor();
    Color getContainerColor();
    ArrayList<ItemInfo> getChildren();

    void addChild(ItemInfo item);
    void removeChild(ItemInfo item);

    ItemInfo next();
    boolean hasNext();
}