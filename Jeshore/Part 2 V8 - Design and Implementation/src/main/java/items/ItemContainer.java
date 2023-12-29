package items;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ItemContainer extends ItemLeaf{
    //Array for children of container
    ArrayList<ItemInfo> children;
    //Index
    private int index = 0;

    //Constructor
    public ItemContainer(String name, ArrayList<ItemInfo> children, double price, double marketValue, double priceAll, double marketValueAll, double x, double y, double l, double w, Color fC, Color cC, Color fillC){
        this.name = name;
        this.children = children;
        this.price = price;
        this.marketValue = marketValue;
        this.priceAll = priceAll;
        this.marketValueAll = marketValueAll;
        posX = x;
        posY = y;
        length = l;
        width = w;
        fontColor = fC;
        containerColor = cC;
        fillColor = fillC;
    }
    //Getter
    public ArrayList<ItemInfo> getChildren(){
        return children;
    }
    //Functions
    public void addChild(ItemInfo item){
        children.add(item);
    }

    public void removeChild(ItemInfo item){
        children.remove(item);
    }
    public boolean hasNext() { // If index has no children, return false. Else
        if (index == children.size()){
            index = 0;
            return false;
        }
        return index < children.size();
    }
    public ItemInfo next(){
        ItemInfo nextItem = this.children.get(index);
        index++;
        return nextItem;
    }

}
