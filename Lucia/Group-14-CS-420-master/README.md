How to run Project: Load entire project folder into your IDE and run Application.java 
                  - Change Desktop screen resolution in Display settings if Farm Dashboard is too big for your screen.  

GitHub repository link: https://github.com/ssrinivasa0520/Group-14-CS-420

# Singleton Design Pattern

The Singleton design pattern in Java is a creational design pattern that ensures a class has only one instance while providing a global point of access to that instance. This means that no matter how many times you request an instance of the class, you will always get the same one.


In this project:

1. The class `DashboardScene` has a private static field `scene` that holds the single instance of the class.
2. The constructor of the class is private, preventing any other class from instantiating it.
3. The `getInstance` method is a static method that provides a way to access the instance of the class. If `scene` is null, it creates a new instance; otherwise, it returns the existing instance.

Usage:

In this project, the overridden start() method in Application class uses DashboardScene to instantiate a scene. This means that no matter how many times you call getInstance(), you always get the same instance. This will prevent creation of multiple dashboards.

The Singleton pattern is often used in scenarios where you want to control access to resources such as database connections, thread pools, or configuration settings. However, it's important to be careful with Singletons, as they can sometimes introduce global state and make code harder to test and reason about.

# Composite Design Pattern

The Composite design pattern is a structural design pattern that allows you to compose objects into tree structures to represent part-whole hierarchies. It lets clients treat individual objects and compositions of objects uniformly.

In simpler terms, the Composite pattern is used when you have objects that can be treated in a hierarchical manner. Each object can be either a leaf (an individual element) or a composite (a collection of elements, which may contain other composites or leaves).

Key components of the Composite pattern:

1. **ItemI**: This is the base interface or abstract class that declares the common operations for both leaf and composite objects. In our project, a component is an interface called `ItemI` that declares methods like `getName()`, setPos() etc.

2. **ItemLeaf**: This represents the individual objects in the composition. It implements the `ItemI` interface. In our project, the `ItemLeaf` class is a leaf, representing individual items.

3. **Item**: This represents the container of components. It also implements the `ItemI` interface. It can hold a collection of child components, which can be either leaves or other composites. In our project, the `Item` class is a composite, representing folders that can contain both items and sub-items.

Usage:

In our project, we have a item container and item representation. Item containers (`Item`) and items (`ItemLeaf`) both implement the `ItemI` interface. Item containers can contain other items or item containers, allowing for a hierarchical structure.

The Composite pattern is particularly useful when you want clients to treat individual objects and compositions of objects uniformly, without having to distinguish between them. This can simplify code and make it more flexible. It's commonly used in GUI frameworks, file systems, and hierarchical data structures.



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



The Adapter Pattern is a structural design pattern that allows the interface of an existing class to be used as another interface. It is often used to make existing classes work with others without modifying their source code. This pattern involves a single class called the adapter that is responsible for joining functionalities of independent or incompatible interfaces.

We have a `TelloDroneController` class which wraps the `TelloControl` class from the Tello SDK. It is used to issue commands to the drone. Our code in `DroneComponent` expects objects in charge of simulation or actual flight to implement `visitItem` and `scanFarm` methods. The object in charge of simulation belongs to the class `DroneAnimator` and the object in charge of actual flight belongs to the class `DroneController`. Both `DroneAnimator` and `DroneController` are adapter classes that implement an interface `TelloDroneAdapter` to become compatible with the rest of our code.

`DroneAnimator` and `DroneController` act as a bridges between the `TelloDroneController` class and the `TelloDroneAdapter` interface, allowing the existing `TelloDroneController` class to be used in contexts where a `TelloDroneController` is expected without modifying the original `TelloDroneController` class. The adapter simply delegates the `visitItem` and `scanFarm` method calls to the methods in the `TelloDroneController` class.