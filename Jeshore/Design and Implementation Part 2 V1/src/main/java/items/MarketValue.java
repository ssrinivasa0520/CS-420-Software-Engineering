package items;

public class MarketValue implements Values {
    public void accept(ValuesVisitor valuesVisitor, ItemInfo selection){
        valuesVisitor.visit(this, selection);
    }
}
