package items;

public class Price implements Values { //price visitor
    public void accept(ValuesVisitor valuesVisitor, ItemInfo selection, ItemInfo parent, ItemInfo parentOfParent) { //accepts visit
        valuesVisitor.visit(this, selection, parent, parentOfParent);
    }
}
