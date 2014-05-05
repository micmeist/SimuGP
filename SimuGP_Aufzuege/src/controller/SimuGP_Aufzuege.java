/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import events.Event;
import events.PassengerArrival;
import java.util.logging.Level;
import java.util.logging.Logger;
import simugp_aufzuege.Building;

/**
 *
 * @author micmeist
 */
public class SimuGP_Aufzuege {

    private static final Logger LOGGER = Logger.getLogger(SimuGP_Aufzuege.class.getName());
    //End time of simulation in seconds
    private static final double maxSimulationTime = 50000;
    //Simulation time in seconds
    private static double simulationTime = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.info("Simulationsstart");

        Building building = new Building(2, 0);

        FutureEventList.getInstance().addPassengerEvent(new PassengerArrival(building.getFloor(0), building.getFloor(1)));

        while (simulationTime < maxSimulationTime) {
            Event event = FutureEventList.getInstance().pollNextEvent();
            simulationTime = event.getEventTime();
            LOGGER.log(Level.INFO, "Simulationszeit: {0}", simulationTime);
            event.execute();
        }
    }

}
