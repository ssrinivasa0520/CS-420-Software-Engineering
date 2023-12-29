package items;

public interface ValuesVisitor { //Visitor interface
    void visit(MarketValue marketValue, ItemInfo selection, ItemInfo parent, ItemInfo parentOfParent);
    void visit(Price price, ItemInfo selection, ItemInfo parent, ItemInfo parentOfParent);
}
