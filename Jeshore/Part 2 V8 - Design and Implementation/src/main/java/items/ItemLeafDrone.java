package items;

import static com.example.designandimplementation.Controller.droneXPos;
import static com.example.designandimplementation.Controller.droneYPos;
public class ItemLeafDrone extends ItemLeaf{
    //Variable
    private int droneCount;

    //Constructor
    public ItemLeafDrone(String name, int droneCount, double price, double marketValue, double x, double y, double l, double w){
        this.name = name;
        this.droneCount = droneCount;
        this.price = price;
        this.marketValue = marketValue;
        posX = x;
        posY = y;
        length = l;
        width = w;
        droneXPos = x;
        droneYPos = y;
    }
    //Getter
    public int getDrone(){
        return droneCount;
    }
    //Functions
    public boolean hasNext() {
        return false;
    }
    public ItemInfo next(){
        return null;
    }

}
