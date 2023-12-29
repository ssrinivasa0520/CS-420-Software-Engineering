package items;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ItemContainerCommandCenter extends ItemLeaf{
    //Array for containers children
    ArrayList<ItemInfo> children;
    //Index
    private int index = 0;
    //Constructor
    public ItemContainerCommandCenter(String name, int cc, ArrayList<ItemInfo> children, double price, double marketValue, double x, double y, double l, double w, Color fC, Color cC){
        this.name = name;
        this.children = children;
        this.price = price;
        this.marketValue = marketValue;
        commandCenter = cc;
        posX = x;
        posY = y;
        length = l;
        width = w;
        fontColor = fC;
        containerColor = cC;
    }
    //Getters
    public int getCommandCenter(){
        return commandCenter;
    }
    public ArrayList<ItemInfo> getChildren(){
        return children;
    }

    //Functions
    public boolean hasNext() {
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
