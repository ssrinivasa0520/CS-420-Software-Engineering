package items;

import java.util.ArrayList;

public class ItemLeaf implements ItemInfo{
    String name = "";
    double value = 0;
    int posX = 0;
    int posY = 0;
    int length = 0;
    int width = 0;
    int commandCenter = 0;
    public ItemLeaf (){

    }
    public ItemLeaf(String name, double value, int x, int y, int l, int w){
        this.name = name;
        this.value = value;
        posX = x;
        posY = y;
        length = l;
        width = w;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public void setPos(int x, int y) {
        this.posX = x;
        this.posY = y;
    }
    public void setDimension(int l, int w){
        this.length = l;
        this.width = w;


    }

    public String getName() {
        return name;
    }
    public double getValue(){
        return value;
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getCommandCenter() {
        return 0;
    }
    public int getDrone(){
        return 0;
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
