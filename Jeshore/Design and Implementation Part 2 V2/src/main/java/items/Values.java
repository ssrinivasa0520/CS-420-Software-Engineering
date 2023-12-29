package items;

public interface Values {
    void accept(ValuesVisitor valuesVisitor, ItemInfo selection, ItemInfo parent);
}
