package items;

import java.io.IOException;
import static com.example.designandimplementation.Application.controllerCall;
public class SimulationAdapter implements AdapterInterface{
    FlightControls flightControls;

    public SimulationAdapter() {
        flightControls = new Drone();
    }
    public void visitItem(ItemInfo selection, String drone) throws IOException, InterruptedException {
        //controllerCall.simulatorButton.setDisable(true);
        //controllerCall.launchButton.setDisable(true);
        String output = flightControls.test();
        if (output.equalsIgnoreCase("connected")){
            int battery = flightControls.getBattery();
            System.out.println(battery);
            flightControls.activateSDK();
            flightControls.takeOff();
            flightControls.moveBack(20);
            flightControls.moveForward(20);
            flightControls.moveLeft(20);
            flightControls.moveRight(20);
            flightControls.landDrone();
            flightControls.endFlight();
        }
        //controllerCall.simulatorButton.setDisable(false);
        //controllerCall.launchButton.setDisable(false);
    }
    public void scanFarm(ItemInfo selection) {

    }
}
