package items;

public interface Values {
    public void accept(ValuesVisitor valuesVisitor, ItemInfo selection);
}
