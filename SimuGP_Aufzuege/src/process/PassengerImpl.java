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

import controller.FutureEventList;
import events.PassengerEntered;
import events.PassengerLeaved;

/**
 *
 * @author micmeist
 */
public class PassengerImpl implements Passenger{

    private final Floor startFloor;
    private final Floor destinationFloor;
    
    //statistics
    private double waitingStartTime;

    public PassengerImpl(Floor startFloor, Floor destinationFloor) {
        this.startFloor = startFloor;
        this.destinationFloor = destinationFloor;
    }

    @Override
    public Floor getSTART_FLOOR() {
        return startFloor;
    }

    @Override
    public Floor getDESTINATION_FLOOR() {
        return destinationFloor;
    }

    @Override
    public double getWaitingStartTime() {
        return waitingStartTime;
    }

    @Override
    public void setWaitingStartTime(double waitingStartTime) {
        this.waitingStartTime = waitingStartTime;
    }

    //Handler
    @Override
    public void handleElevatorArrival(Elevator elevator) {
        planEnteredElevatorEvent(elevator);
    }

    @Override
    public void handleArrivalAtFloor(Floor floor, Elevator elevator) {
        if(destinationFloor.equals(floor)){
            planLeaveElevatorEvent(elevator);
        }
    }
    
    //Planer
    private void planEnteredElevatorEvent(Elevator elevator) {
        elevator.setState(new StateWaiting(elevator));
        FutureEventList.getInstance().addPassengerEvent(new PassengerEntered(this, elevator));
    }

    private void planLeaveElevatorEvent(Elevator elevator) {
        elevator.setState(new StateWaiting(elevator));
        FutureEventList.getInstance().addPassengerEvent(new PassengerLeaved(this, elevator));
    }

}
