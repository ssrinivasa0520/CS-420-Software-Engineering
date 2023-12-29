package items;

public class Price implements Values {
    public void accept(ValuesVisitor valuesVisitor, ItemInfo selection) {
        valuesVisitor.visit(this, selection);
    }
}
