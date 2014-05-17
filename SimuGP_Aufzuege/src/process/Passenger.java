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
public class Passenger implements Process {

    private final AbstractFloor startFloor;
    private final AbstractFloor destinationFloor;

    public Passenger(AbstractFloor startFloor, AbstractFloor destinationFloor) {
        this.startFloor = startFloor;
        this.destinationFloor = destinationFloor;
    }

    public AbstractFloor getSTART_FLOOR() {
        return startFloor;
    }

    public AbstractFloor getDESTINATION_FLOOR() {
        return destinationFloor;
    }

    //Handler
    public void handleElevatorArrival(Elevator elevator) {
        planEnterElevatorEvent(elevator);
    }

    public void handleArrivalAtFloor(AbstractFloor floor, Elevator elevator) {
        if(destinationFloor.equals(floor)){
            planLeaveElevatorEvent(elevator);
        }
    }
    
    //Planer
    private void planEnterElevatorEvent(Elevator elevator) {
        elevator.setState(new StateWaiting(elevator));
        FutureEventList.getInstance().addPassengerEvent(new PassengerEntered(this, elevator));
    }

    private void planLeaveElevatorEvent(Elevator elevator) {
        elevator.setState(new StateWaiting(elevator));
        FutureEventList.getInstance().addPassengerEvent(new PassengerLeaved(this, elevator));
    }

}
