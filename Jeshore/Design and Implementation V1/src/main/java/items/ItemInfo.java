package items;

import java.util.ArrayList;

public interface ItemInfo {
    public void setName (String name);
    public String getName();

    public ArrayList<ItemInfo> getChildren();
    public void addChild(ItemInfo item);
    public void removeChild(ItemInfo item);

    public ItemInfo next();
    public boolean hasNext();
}