package items;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class ItemContainer extends ItemLeaf{
    ArrayList<ItemInfo> children;
    private int index = 0;

    public ItemContainer(String name, ArrayList<ItemInfo> children){
        this.name = name;
        this.children = children;
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
