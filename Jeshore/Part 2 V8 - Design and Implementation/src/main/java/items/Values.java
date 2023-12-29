package items;

public interface Values { //Visitor interface
    void accept(ValuesVisitor valuesVisitor, ItemInfo selection, ItemInfo parent, ItemInfo parentOfParent); //acceptor call
}
