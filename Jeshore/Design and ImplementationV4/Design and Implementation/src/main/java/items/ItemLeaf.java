package items;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ItemLeaf implements ItemInfo{
    String name = "";
    double value = 0;
    double posX = 0;
    double posY = 0;
    double length = 0;
    double width = 0;
    int commandCenter = 0;
    Color fontColor = Color.BLACK;
    Color containerColor = Color.BLACK;
    public ItemLeaf (){

    }
    public ItemLeaf(String name, double value, double x, double y, double l, double w, Color fC, Color cC){
        this.name = name;
        this.value = value;
        posX = x;
        posY = y;
        length = l;
        width = w;
        fontColor = fC;
        containerColor = cC;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public void setPos(double x, double y) {
        this.posX = x;
        this.posY = y;
    }
    public void setDimension(double l, double w){
        this.length = l;
        this.width = w;


    }
    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public void setContainerColor(Color containerColor) {
        this.containerColor = containerColor;
    }

    public String getName() {
        return name;
    }
    public double getValue(){
        return value;
    }
    public double getPosX(){
        return posX;
    }
    public double getPosY(){
        return posY;
    }
    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public int getCommandCenter() {
        return 0;
    }
    public int getDrone(){
        return 0;
    }


    public Color getFontColor() {
        return fontColor;
    }


    public Color getContainerColor() {
        return containerColor;
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