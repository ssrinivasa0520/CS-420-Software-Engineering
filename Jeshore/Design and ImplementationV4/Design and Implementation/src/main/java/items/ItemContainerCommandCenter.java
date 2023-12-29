package items;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ItemContainerCommandCenter extends ItemLeaf{
    ArrayList<ItemInfo> children;
    private int index = 0;

    public ItemContainerCommandCenter(String name, int cc, ArrayList<ItemInfo> children, double value, double x, double y, double l, double w, Color fC, Color cC){
        this.name = name;
        this.children = children;
        this.value = value;
        commandCenter = cc;
        posX = x;
        posY = y;
        length = l;
        width = w;
        fontColor = fC;
        containerColor = cC;
    }
    public int getCommandCenter(){
        return commandCenter;
    }
    public ArrayList<ItemInfo> getChildren(){
        return children;
    }

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
