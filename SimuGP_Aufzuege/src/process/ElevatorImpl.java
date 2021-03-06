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
public class ElevatorImpl implements Elevator  {

    private static final Logger logger = Logger.getLogger(ElevatorImpl.class.getName());

    private final int CAPACITY;
    //Speed in meter/seconds
    private final double SPEED;
    private final double TIME_TO_MOVE_DOOR;
    private final ArrayDeque<Passenger> passengers = new ArrayDeque();

    private Floor currentFloor;
    //Null if elevator is not moving
    private Floor destination = null;
    private Floor callingFloor = null;
    private ElevatorState currentState = new StateWaitingEmpty(this);

    //Statistics
    private int numberOfPassengersTransported = 0;
    private int numberOfSituationsCapacityReached = 0;

    /**
     *
     * @param capacity
     * @param speed
     * @param currentFloor
     * @param TIME_TO_MOVE_DOOR
     */
    public ElevatorImpl(int capacity, double speed, Floor currentFloor, double TIME_TO_MOVE_DOOR) {
        this.CAPACITY = capacity;
        this.SPEED = speed;
        this.TIME_TO_MOVE_DOOR = TIME_TO_MOVE_DOOR;
        this.currentFloor = currentFloor;
    }

    /**
     *
     * @return
     */
    @Override
    public int getNumberOfPassengersTransported() {
        return numberOfPassengersTransported;
    }

    /**
     *
     * @return
     */
    @Override
    public int getCurrentNumberOfPassengers() {
        return passengers.size();
    }

    /**
     *
     * @return
     */
    @Override
    public int getNumberOfSituationsCapacityReached() {
        return numberOfSituationsCapacityReached;
    }

    /**
     *
     * @return
     */
    @Override
    public Floor getDestination() {
        return destination;
    }

    /**
     *
     * @return
     */
    @Override
    public Floor getCurrentFloor() {
        return currentFloor;
    }

    /**
     *
     * @param callingFloor
     */
    @Override
    public void setCallingFloor(Floor callingFloor) {
        this.callingFloor = callingFloor;
    }

    /**
     *
     * @param currentState
     */
    @Override
    public void setState(AbstractElevatorState currentState) {
        this.currentState = currentState;
    }
    
    //Handler

    /**
     *
     * @param floor
     */
        @Override
    public void handleArrival(Floor floor) {
        logger.log(Level.FINE, "Elevator arrived at floor {0}", floor.getFloorNumber());
        currentFloor = floor;
        destination = null;
        notifyArrivalPassenger();
        if(callingFloor != null){
            planStartMoving(callingFloor);
            callingFloor = null;
        }
    }

    /**
     *
     * @param floor
     */
    @Override
    public void handleStartMoving(Floor floor) {
        logger.log(Level.FINE, "Elevator start moving form floor {0} to floor {1}", new Object[]{currentFloor.getFloorNumber(), floor.getFloorNumber()});
        currentState = new StateMoving(this);
        destination = floor;
        planElevatorArrival(floor);
    }

    /**
     *
     * @param floor
     */
    @Override
    public void handleCall(Floor floor) {
        currentState.handleCall(floor);
    }

    /**
     *
     * @param passenger
     */
    @Override
    public void handlePassengerLeaved(Passenger passenger) {
        passengers.remove(passenger);
        logger.log(Level.FINE, "Passenger leaved elevator. {0} passenger(s) in elevator now", passengers.size());
        numberOfPassengersTransported++;
        notifyArrivalPassenger();
    }

    /**
     *
     * @param passenger
     */
    @Override
    public void handlePassengerEntered(Passenger passenger) {
        passengers.add(passenger);
        logger.log(Level.FINE, "Passenger entered elevator. {0} passenger(s) in elevator now", passengers.size());
        //More passengers should enter when capacity is not reached
        if (passengers.size() < CAPACITY ) {
            notifyArrivalFloor();
        } else {
            logger.fine("Elevator capacity reached");
            numberOfSituationsCapacityReached++;
        }
        planStartMoving(passenger.getDESTINATION_FLOOR());

    }

    //Planer

    /**
     *
     * @param floor
     */
        @Override
    public void planElevatorArrival(Floor floor) {
        double secondsToArrival = calcSecondsToArrival(currentFloor.getFloorNumber(), floor.getFloorNumber());
        FutureEventList.getInstance().addElevatorArrivalEvent(new ElevatorArrival(this, secondsToArrival, floor));
    }

    /**
     *
     * @param floorToMoveTo
     */
    @Override
    public void planStartMoving(Floor floorToMoveTo) {
        FutureEventList.getInstance().addElevatorStartMovingEvent(new ElevatorStartMoving(this, floorToMoveTo));
    }

    //Notifier
    private void notifyArrivalPassenger() {
        //Notify passengers in elevator. If Elevator is empty notify floor
        if (passengers.isEmpty()) {
            logger.log(Level.FINE, "No passengers left in elevator, passengers on floor {0} can start entering elevator.", currentFloor.getFloorNumber());
            notifyArrivalFloor();
        } else {
            logger.log(Level.FINE, "{0} passenger(s) in elevator now. Continue leaving elevator", passengers.size());
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
