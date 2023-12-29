package items;

import java.util.ArrayList;

public interface ItemInfo {
    //Set
    void setName (String name);
    void setValue (double price);
    void setPos (int x, int y);
    void setDimension (int length, int width);

    //Get
    String getName();
    double getValue();
    int getPosX();
    int getPosY();
    int getLength();
    int getWidth();
    int getCommandCenter();
    int getDrone();
    ArrayList<ItemInfo> getChildren();

    void addChild(ItemInfo item);
    void removeChild(ItemInfo item);

    ItemInfo next();
    boolean hasNext();
}