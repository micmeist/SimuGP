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

/**
 *
 * @author micmeist
 */
public class Elevator implements Process {

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

    //Handler
    public void handleArrival(Floor floor) {
        currentFloor = floor;
        destination = null;
        notifyArrivalPassenger();
        notifyArrivalFloor();
    }

    public void handleStartMoving(Floor floor) {
        destination = floor;
        planElevatorArrival(floor);
    }

    public void handleCall(Floor floor) {
        if (destination != null) {
            if (destination.getFloorNumber() != floor.getFloorNumber()) {
                planStartMoving(floor);
            }
        } else {
            planStartMoving(floor);
        }
    }

    public void handlePassengerLeaved(Passenger passenger) {
        passengers.remove(passenger);
        numberOfPassengersTransported++;
        notifyArrivalPassenger();
    }

    public void handlePassengerEntered(Passenger passenger) {
        passenger.getSTART_FLOOR().removePassenger(passenger);
        if (passengers.isEmpty()) {
            planStartMoving(passenger.getDESTINATION_FLOOR());
        }
        passengers.add(passenger);
        //More passengers should come when capacity is not reached
        if (passengers.size() < CAPACITY) {
            notifyArrivalFloor();
        }

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
        if (!passengers.isEmpty()) {
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
