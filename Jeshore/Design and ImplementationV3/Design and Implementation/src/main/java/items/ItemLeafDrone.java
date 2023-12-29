package items;

public class ItemLeafDrone extends ItemLeaf{
    private int droneCount;


    public ItemLeafDrone(String name, int droneCount, double value, double x, double y, double l, double w){
        this.name = name;
        this.droneCount = droneCount;
        this.value = value;
        posX = x;
        posY = y;
        length = l;
        width = w;
    }

    public int getDrone(){
        return droneCount;
    }
    public boolean hasNext() {
        return false;
    }
    public ItemInfo next(){
        return null;
    }

}
