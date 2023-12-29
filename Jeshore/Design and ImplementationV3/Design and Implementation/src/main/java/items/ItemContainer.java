package items;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ItemContainer extends ItemLeaf{
    ArrayList<ItemInfo> children;
    private int index = 0;

    public ItemContainer(String name, ArrayList<ItemInfo> children, double value, double x, double y, double l, double w, Color fC, Color cC){
        this.name = name;
        this.children = children;
        this.value = value;
        posX = x;
        posY = y;
        length = l;
        width = w;
        fontColor = fC;
        containerColor = cC;

    }

    public void addChild(ItemInfo item){
        children.add(item);
    }

    public void removeChild(ItemInfo item){
        children.remove(item);
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
