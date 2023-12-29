package items;

import java.util.ArrayList;

public class valuesDisplayVisitor extends ItemLeaf implements ValuesVisitor {

    public void visit(MarketValue marketValue, ItemInfo selection) {
        ArrayList<ItemInfo> child = selection.getChildren();
        System.out.println(selection.getMarketValue());
    }
    public void visit(Price price, ItemInfo selection) {
        ArrayList<ItemInfo> child = selection.getChildren();
        System.out.println(selection.getPrice());
    }
}
