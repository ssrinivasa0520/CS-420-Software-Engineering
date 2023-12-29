How to run Project: Load entire project folder into your IDE and run Application.java 
                  - Change Desktop screen resolution in Display settings if Farm Dashboard is too big for your screen.  

GitHub repository link: https://github.com/ssrinivasa0520/Group-14-CS-420

The Adapter Pattern is a structural design pattern that allows the interface of an existing class to be used as another interface. It is often used to make existing classes work with others without modifying their source code. This pattern involves a single class called the adapter that is responsible for joining functionalities of independent or incompatible interfaces.

We have a `TelloDroneController` class which wraps the `TelloControl` class from the Tello SDK. It is used to issue commands to the drone. Our code in `DroneComponent` expects objects in charge of simulation or actual flight to implement `visitItem` and `scanFarm` methods. The object in charge of simulation belongs to the class `DroneAnimator` and the object in charge of actual flight belongs to the class `DroneController`. Both `DroneAnimator` and `DroneController` are adapter classes that implement an interface `TelloDroneAdapter` to become compatible with the rest of our code.

`DroneAnimator` and `DroneController` act as a bridges between the `TelloDroneController` class and the `TelloDroneAdapter` interface, allowing the existing `TelloDroneController` class to be used in contexts where a `TelloDroneController` is expected without modifying the original `TelloDroneController` class. The adapter simply delegates the `visitItem` and `scanFarm` method calls to the methods in the `TelloDroneController` class.
