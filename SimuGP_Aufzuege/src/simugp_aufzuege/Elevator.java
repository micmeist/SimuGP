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
package simugp_aufzuege;

import controller.FutureEventList;
import events.ElevatorStartMoving;
import java.util.Stack;

/**
 *
 * @author micmeist
 */
public class Elevator implements Process {

    private final int CAPACITY;
    //Speed in meter/seconds
    private final int SPEED;
    private final double TIME_TO_MOVE_DOOR;
    private final Stack<Passenger> passengers;

    private Floor currentFloor;
    //Null if elevator is not moving
    private Floor destination = null;


    public Elevator(int capacity, int speed, Floor currentFloor, double TIME_TO_MOVE_DOOR) {
        this.CAPACITY = capacity;
        this.SPEED = speed;
        this.TIME_TO_MOVE_DOOR = TIME_TO_MOVE_DOOR;
        this.passengers = new Stack<>();
        this.currentFloor = currentFloor;
    }

    //Handler
    public void handleArrival(Floor floor) {
        currentFloor = floor;
        destination = null;
        notifyArrivalPassengers();
        notifyArrivalFloor();
    }

    public void handleStartMoving(Floor floor) {
        destination = floor;
        planElevatorArrival(floor);
    }

    public void handleCall(Floor floor) {
        if (destination != null) {
           if(destination.getFloorNumber() != floor.getFloorNumber()){
               planStartMoving(floor);
           }
        } else {
            planStartMoving(floor);
        }
    }

    //Planer
    private void planElevatorArrival(Floor floor) {
        double secondsToArrival = calcSecondsToArrival(currentFloor.getFloorNumber(), floor.getFloorNumber());
    }

    private void planStartMoving(Floor floor) {
        FutureEventList.getInstance().addElevatorStartMovingEvent(new ElevatorStartMoving(this));
    }

    //Notifier
    private void notifyArrivalPassengers() {
        passengers.lastElement().handleArrivalAtFloor(currentFloor);
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
