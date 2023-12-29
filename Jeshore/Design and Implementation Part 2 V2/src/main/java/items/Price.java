package items;

public class Price implements Values {
    public void accept(ValuesVisitor valuesVisitor, ItemInfo selection, ItemInfo parent) {
        valuesVisitor.visit(this, selection, parent);
    }
}
