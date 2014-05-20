/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import events.Event;
import events.PassengerArrivalOnGround;
import java.util.logging.Level;
import java.util.logging.Logger;
import process.Building;
import process.Floor;

/**
 *
 * @author micmeist
 */
public class SimuGP_Aufzuege {

    private static final Logger LOGGER = Logger.getLogger(SimuGP_Aufzuege.class.getName());
    //End time of simulation in seconds
    private static final double maxSimulationTime = 86400;
    private static Building building;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.info("Start of simulation");

        building = new Building(2, 0);
        FutureEventList.getInstance().addPassengerEvent(new PassengerArrivalOnGround(building.getFloor(0), building.getFloor(1)));

        while (GlobalVariables.simulationTime < maxSimulationTime) {
            try {
                Event event = FutureEventList.getInstance().pollNextEvent();
                GlobalVariables.simulationTime = event.getEventTime();
                LOGGER.log(Level.FINE, "Simulationszeit: {0}s Event: {1} Passengers Floor 0: {2} Passengers Floor 1: {3} Passengers Elevator: {4}",
                        new Object[]{GlobalVariables.simulationTime, event.getClass().getSimpleName(), building.getFloor(0).getCurrentNumberOfPassengersInQueue(), building.getFloor(1).getCurrentNumberOfPassengersInQueue(), building.getElevator().getCurrentNumberOfPassengers()});
                event.execute();
            } catch (NullPointerException e) {
                LOGGER.log(Level.WARNING, "Empty FutureEventList.");
                break;
            }
        }
        LOGGER.info("End of simulation");
        printStatistics();
    }

    private static void printStatistics() {
        Floor floor = (Floor) building.getFloor(1);
        LOGGER.log(Level.INFO, "Number of transported passengers: {0} \n"
                + "Number of situations elevator capacity reached: {1} \n"
                + "Number of passengers in queue on floor 0: {2} \n"
                + "Number of passengers on floor 1: {3} \n"
                + "Number of passengers in queue on floor 1: {4} \n"
                + "Number of passengers in elevator: {5} \n"
                + "Longest time passenger waiting on elevator: {6}s",
                new Object[]{
                    building.getElevator().getNumberOfPassengersTransported(),
                    building.getElevator().getNumberOfSituationsCapacityReached(),
                    building.getFloor(0).getCurrentNumberOfPassengersInQueue(),
                    floor.getNumberOfPassangersOnFloor(),
                    floor.getCurrentNumberOfPassengersInQueue(),
                    building.getElevator().getCurrentNumberOfPassengers(),
                    Statistics.getMaxWaitingTime()
                });

    }

}
