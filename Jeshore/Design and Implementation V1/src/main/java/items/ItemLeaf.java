package items;

import java.util.ArrayList;

public class ItemLeaf implements ItemInfo{
    String name = "";

    public ItemLeaf (){

    }
    public ItemLeaf(String name){
        this.name = name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ItemInfo> getChildren() {
        return null;
    }

    public void addChild(ItemInfo item) {

    }

    public void removeChild(ItemInfo item) {

    }

    public ItemInfo next() {
        return null;
    }

    public boolean hasNext() {
        return false;
    }
    public String toString() {
        return name;
    }
}
