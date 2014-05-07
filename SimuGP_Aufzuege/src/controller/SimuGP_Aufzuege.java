/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import events.Event;
import events.PassengerArrival;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import process.Building;

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
        LOGGER.info("Simulationsstart");

        building = new Building(2, 0);
        FutureEventList.getInstance().addPassengerEvent(new PassengerArrival(building.getFloor(0), building.getFloor(1)));

        while (GlobalVariables.simulationTime < maxSimulationTime) {
            try {
                Event event = FutureEventList.getInstance().pollNextEvent();
                GlobalVariables.simulationTime = event.getEventTime();
                LOGGER.log(Level.INFO, "Simulationszeit: {0}s Event: {1} Passengers Floor 0: {2} Passengers Floor 1: {3} Passengers Elevator: {4}",
                        new Object[]{GlobalVariables.simulationTime, event.getClass().getSimpleName(), building.getFloor(0).getCurrentNumberOfPassengers(), building.getFloor(1).getCurrentNumberOfPassengers(), building.getElevator().getCurrentNumberOfPassengers()});
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
        LOGGER.log(Level.INFO, "Number of transported passengers: {0}", building.getElevator().getNumberOfPassengersTransported());
    }

}
