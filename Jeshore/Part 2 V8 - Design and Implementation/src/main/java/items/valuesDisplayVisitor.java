package items;

import java.util.ArrayList;
public class valuesDisplayVisitor implements ValuesVisitor { //visit display
    public void visit(Price price, ItemInfo selection, ItemInfo parent, ItemInfo parentOfParent) { //calls calculation
        calculateTotalPrice(selection, parent, parentOfParent);
    }
    public void visit(MarketValue marketValue, ItemInfo selection, ItemInfo parent, ItemInfo parentOfParent) { //calls calculation
        calculateTotalMarketValue(selection, parent, parentOfParent);
    }
    private void calculateTotalPrice(ItemInfo selection, ItemInfo parent, ItemInfo parentOfParent) { //calculates total price
        ArrayList<ItemInfo> child = selection.getChildren();
        double totalValueOfParentOfParent = 0;
        double totalValueOfParent = 0;
        double totalValueOfChild = selection.getPrice();
        int i = 0;
        int j = 0;
        if (child != null) {
            while (i < child.size()) {
                if (child.get(i).getChildren() != null) {
                    ArrayList<ItemInfo> childOfChild = child.get(i).getChildren();
                    while (j < childOfChild.size()) {
                        totalValueOfChild = totalValueOfChild + childOfChild.get(j).getPrice();
                        j++;
                    }
                }
                totalValueOfChild = totalValueOfChild + child.get(i).getPrice();
                i++;
            }
            selection.setPriceAll(totalValueOfChild);
        }
        i = 0;
        if (parent != null && !parent.getName().equalsIgnoreCase("farm")){
            while (i < parent.getChildren().size()){
                totalValueOfParent = totalValueOfParent + parent.getChildren().get(i).getPriceAll();
                i++;
            }
            parent.setPriceAll(totalValueOfParent + parent.getPrice());
        }
        j = 0;
        if(parentOfParent != null && !parentOfParent.getName().equalsIgnoreCase("farm")){
            while (j < parentOfParent.getChildren().size()){
                totalValueOfParentOfParent = totalValueOfParentOfParent + parentOfParent.getChildren().get(j).getPriceAll();
                j++;
            }
            parentOfParent.setPriceAll(totalValueOfParentOfParent + parentOfParent.getPrice());
        }
    }
    private void calculateTotalMarketValue(ItemInfo selection, ItemInfo parent, ItemInfo parentOfParent) { // calculates total market value
        ArrayList<ItemInfo> child = selection.getChildren();
        double totalValueOfParentOfParent = 0;
        double totalValueOfParent = 0;
        double totalValueOfChild = selection.getMarketValue();
        int i = 0;
        int j = 0;
        if (child != null) {
            while (i < child.size()) {
                if (child.get(i).getChildren() != null) {
                    ArrayList<ItemInfo> childOfChild = child.get(i).getChildren();
                    while (j < childOfChild.size()) {
                        totalValueOfChild = totalValueOfChild + childOfChild.get(j).getPrice();
                        j++;
                    }
                }
                totalValueOfChild = totalValueOfChild + child.get(i).getMarketValue();
                i++;
            }
            selection.setMarketValueAll(totalValueOfChild);
        }
        i = 0;
        if (parent != null && !parent.getName().equalsIgnoreCase("farm")) {
            while (i < parent.getChildren().size()) {
                totalValueOfParent = totalValueOfParent + parent.getChildren().get(i).getMarketValueAll();
                i++;
            }
            parent.setMarketValueAll(totalValueOfParent + parent.getMarketValue());
        }
        j = 0;
        if (parentOfParent != null && !parentOfParent.getName().equalsIgnoreCase("farm")) {
            while (j < parentOfParent.getChildren().size()) {
                totalValueOfParentOfParent = totalValueOfParentOfParent + parentOfParent.getChildren().get(j).getMarketValueAll();
                j++;
            }
            parentOfParent.setMarketValueAll(totalValueOfParentOfParent + parentOfParent.getMarketValue());
        }
    }
}
