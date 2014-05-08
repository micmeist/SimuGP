/*
 * The MIT License
 *
 * Copyright 2014 micmeist.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package process;

import controller.GlobalVariables;
import controller.FutureEventList;
import events.ElevatorArrival;
import events.ElevatorStartMoving;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author micmeist
 */
public class Elevator implements Process {
    
    private static final Logger logger = Logger.getLogger(Elevator.class.getName());

    private final int CAPACITY;
    //Speed in meter/seconds
    private final int SPEED;
    private final double TIME_TO_MOVE_DOOR;
    private final ArrayDeque<Passenger> passengers;

    private Floor currentFloor;
    //Null if elevator is not moving
    private Floor destination = null;

    //Statistics
    private int numberOfPassengersTransported = 0;
    private int numberOfSituationsCapacityReached = 0;

    public Elevator(int capacity, int speed, Floor currentFloor, double TIME_TO_MOVE_DOOR) {
        this.CAPACITY = capacity;
        this.SPEED = speed;
        this.TIME_TO_MOVE_DOOR = TIME_TO_MOVE_DOOR;
        this.passengers = new ArrayDeque();
        this.currentFloor = currentFloor;
    }

    public int getNumberOfPassengersTransported() {
        return numberOfPassengersTransported;
    }

    public int getCurrentNumberOfPassengers() {
        return passengers.size();
    }

    public int getNumberOfSituationsCapacityReached() {
        return numberOfSituationsCapacityReached;
    }

    //Handler
    public void handleArrival(Floor floor) {
        logger.log(Level.INFO, "Elevator arrived at floor {0}", floor.getFloorNumber());
        currentFloor = floor;
        destination = null;
        notifyArrivalPassenger();
    }

    public void handleStartMoving(Floor floor) {
        logger.log(Level.INFO, "Elevator start moving form floor {0} to floor {1}", new Object[]{currentFloor.getFloorNumber(), floor.getFloorNumber()});
        destination = floor;
        planElevatorArrival(floor);
    }

    public void handleCall(Floor floor) {
        if (destination != null) {
            if (destination.getFloorNumber() != floor.getFloorNumber()) {
                logger.log(Level.INFO, "Elevator called to floor {0} while moving", floor.getFloorNumber());
                planStartMoving(floor);
            }
        } else {
            logger.log(Level.INFO, "Elevator on floor {0} called to floor {1}", new Object[]{currentFloor.getFloorNumber() ,floor.getFloorNumber()});
            planStartMoving(floor);
        }
    }

    public void handlePassengerLeaved(Passenger passenger) {
        passengers.remove(passenger);
        logger.log(Level.INFO, "Passenger leaved elevator. {0} passenger(s) in elevator now", passengers.size());
        numberOfPassengersTransported++;
        notifyArrivalPassenger();
    }

    public void handlePassengerEntered(Passenger passenger) {
        passenger.getSTART_FLOOR().removePassenger(passenger);
        passengers.add(passenger);
        logger.log(Level.INFO, "Passenger entered elevator. {0} passenger(s) in elevator now", passengers.size());
        //More passengers should come when capacity is not reached
        if (passengers.size() < CAPACITY) {
            notifyArrivalFloor();
        }else{
            logger.info("Elevator capacity reached");
            numberOfSituationsCapacityReached++;
        }
        handleCall(passenger.getDESTINATION_FLOOR());

    }

    //Planer
    private void planElevatorArrival(Floor floor) {
        double secondsToArrival = calcSecondsToArrival(currentFloor.getFloorNumber(), floor.getFloorNumber());
        FutureEventList.getInstance().addElevatorArrivalEvent(new ElevatorArrival(this, secondsToArrival, floor));
    }

    private void planStartMoving(Floor floorToMoveTo) {
        FutureEventList.getInstance().addElevatorStartMovingEvent(new ElevatorStartMoving(this, floorToMoveTo));
    }

    //Notifier
    private void notifyArrivalPassenger() {
        if (passengers.isEmpty()) {
            logger.log(Level.INFO, "No passengers left in elevator, passengers on floor {0} can start entering elevator.", currentFloor.getFloorNumber());
            notifyArrivalFloor();
        }else{
            logger.log(Level.INFO, "{0} passenger(s) in elevator now. Continue leaving elevator", passengers.size());
            passengers.getLast().handleArrivalAtFloor(currentFloor, this);
        }
    }

    private void notifyArrivalFloor() {
        currentFloor.handleElevatorArrival();
    }

    //Elevator logic
    private double calcSecondsToArrival(int startFloor, int destinationFloor) {
        int diffFloors;
        double seconds;

        if (destinationFloor > startFloor) {
            diffFloors = destinationFloor - startFloor;
        } else {
            diffFloors = startFloor - destinationFloor;
        }

        if (diffFloors == 0) {
            seconds = TIME_TO_MOVE_DOOR;
        } else {
            seconds = ((diffFloors * GlobalVariables.FLOOR_HIGHT) / SPEED) + TIME_TO_MOVE_DOOR * 2;
        }

        return seconds;
    }

}
