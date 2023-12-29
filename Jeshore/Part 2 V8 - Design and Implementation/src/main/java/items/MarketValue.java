package items;

public class MarketValue implements Values { //Marketvalue visitor
    public void accept(ValuesVisitor valuesVisitor, ItemInfo selection, ItemInfo parent, ItemInfo parentOfParent){ //accepts visit
        valuesVisitor.visit(this, selection, parent, parentOfParent);

    }
}
