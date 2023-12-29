package items;

import java.util.ArrayList;

import static com.example.designandimplementation.Controller.totalFarmMarketValue;
import static com.example.designandimplementation.Controller.totalFarmPrice;

public class valuesDisplayVisitor extends ItemLeaf implements ValuesVisitor {
    public void visit(Price price, ItemInfo selection, ItemInfo parent) {
        calculateTotalPrice(selection, parent);
    }
    public void visit(MarketValue marketValue, ItemInfo selection, ItemInfo parent) {
        calculateTotalMarketValue(selection, parent);
    }
    private void calculateTotalPrice(ItemInfo selection, ItemInfo parent) {
        ArrayList<ItemInfo> child = selection.getChildren();
        double totalValue = selection.getPrice();
        int i = 0;
        int j = 0;
        if (child != null) {
            while (i < child.size()){ //Going through all children of an item container
                if (child.get(i).getChildren() != null){ //If the child of item container exists
                    ArrayList<ItemInfo> childOfChild = child.get(i).getChildren(); //get child of child[i]
                    while (j < childOfChild.size()){
                        totalValue = totalValue + childOfChild.get(j).getPrice(); //get price children and sum them
                        j++;
                    }
                }
                totalValue = totalValue + child.get(i).getPrice();
                i++;
            }
        }
        parent.setPriceAll(totalValue + parent.getPriceAll());
        selection.setPriceAll(totalValue);
        totalFarmPrice = totalFarmPrice + totalValue;
    }
    private void calculateTotalMarketValue(ItemInfo selection, ItemInfo parent) {
        ArrayList<ItemInfo> child = selection.getChildren();
        double totalValue = selection.getMarketValue();
        int i = 0;
        int j = 0;
        if (child != null) {
            while (i < child.size()) {
                if (child.get(i).getChildren() != null) {
                    ArrayList<ItemInfo> childOfChild = child.get(i).getChildren();
                    while (j < childOfChild.size()) {
                        totalValue = totalValue + childOfChild.get(j).getMarketValue();
                        j++;
                    }
                }
                totalValue = totalValue + child.get(i).getMarketValue();
                i++;
            }
        }
        parent.setMarketValueAll(totalValue + parent.getMarketValueAll());
        selection.setMarketValueAll(totalValue);
        totalFarmMarketValue = totalFarmMarketValue + totalValue;

    }
}
