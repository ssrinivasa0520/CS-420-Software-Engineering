How to run Project: Load entire project folder into your IDE and run Application.java 
                  - Change Desktop screen resolution in Display settings if Farm Dashboard is too big for your screen.  

GitHub repository link: https://github.com/ssrinivasa0520/Group-14-CS-420

The Visitor pattern is a behavioral design pattern that allows you to define a new operation (or algorithm) without changing the classes of the elements on which it operates. It achieves this by separating the algorithm from the elements it operates on.

Here's how the Visitor pattern works:

1. **Element Interface**: This defines the interface for the elements that will be visited by the visitor.

2. **Concrete Elements**: These are the actual classes that implement the element interface. They hold the data and possibly some logic related to the data.

3. **Visitor Interface**: This interface declares the methods that correspond to the different types of elements. Each method in this interface represents a different operation that can be performed on the elements.

4. **Concrete Visitor**: This is the class that implements the visitor interface. It contains the actual algorithm that operates on the elements. Each method in the visitor corresponds to a specific operation on a specific type of element.

5. **Client**: This is the code that sets up the elements and the visitor, and triggers the visitation process.

In our project, we have a `ItemI` interface which is implemented by `Item` class. Each item can accept a visitor, which in turn calls the appropriate `visit` method on the visitor.

We also have a `VisitorI` interface with `visit` methods for `Item`. The `PurchasePriceVisitor` and `MarketValueVisitor` are concrete visitors that implements the `VisitorI` interface.

When the `mousePress` event on the tree view is triggered, that is when an item or item container is selected in the tree view, the methods defined in the `PriceLabelComponent` class are called by the `handleItemViewMousePress` event handler defined in the `DashboardController` class. These methods in turn call the `visit` method on the objects instantiated on the `PurchasePriceVisitor` and `MarketValueVisitor` classes with the selected `ItemI` from the tree view as the argument.

This way, if we want to add a new operation (e.g., calculating total area occupied), we can create a new visitor without modifying the existing shape classes. This promotes separation of concerns and makes it easier to extend functionality in the future.