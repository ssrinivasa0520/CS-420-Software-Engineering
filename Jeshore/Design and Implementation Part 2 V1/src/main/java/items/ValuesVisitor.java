package items;

public interface ValuesVisitor {
    void visit(MarketValue marketValue, ItemInfo selection);
    void visit(Price price, ItemInfo selection);
}
