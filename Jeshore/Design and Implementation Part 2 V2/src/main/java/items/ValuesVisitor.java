package items;

public interface ValuesVisitor {
    void visit(MarketValue marketValue, ItemInfo selection, ItemInfo parent);
    void visit(Price price, ItemInfo selection, ItemInfo parent);
}
