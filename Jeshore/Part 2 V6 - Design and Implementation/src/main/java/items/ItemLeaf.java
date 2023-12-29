package items;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ItemLeaf implements ItemInfo{
    //Variables
    String name = "";
    double price = 0;
    double marketValue = 0;
    double priceAll = 0;
    double marketValueAll = 0;
    double posX = 0;
    double posY = 0;
    double length = 0;
    double width = 0;
    Color fontColor = Color.BLACK;
    Color containerColor = Color.BLACK;
    Color fillColor = Color.WHITE;
    //Constructors
    public ItemLeaf (){

    }
    public ItemLeaf(String name, double price, double marketValue, double x, double y, double l, double w, Color fC, Color cC, Color fillC){
        this.name = name;
        this.price = price;
        this.marketValue = marketValue;
        posX = x;
        posY = y;
        length = l;
        width = w;
        fontColor = fC;
        containerColor = cC;
        fillColor = fillC;
    }
    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setMarketValue(double marketValue){
        this.marketValue = marketValue;
    }
    public void setPriceAll(double priceAll){this.priceAll = priceAll;}
    public void setMarketValueAll(double marketValueAll){this.marketValueAll = marketValueAll;}
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

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    //Getters
    public String getName() {
        return name;
    }
    public double getPrice(){
        return price;
    }
    public double getMarketValue(){
        return marketValue;
    }
    public double getMarketValueAll(){
        return marketValueAll;
    }
    public double getPriceAll(){
        return priceAll;
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

    public Color getFillColor() {
        return fillColor;
    }

    public ArrayList<ItemInfo> getChildren() {
        return null;
    }
    //Functions
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
