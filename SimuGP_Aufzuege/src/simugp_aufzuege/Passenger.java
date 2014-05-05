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

/**
 *
 * @author micmeist
 */
public class Passenger implements Process {

    private final Floor startFloor;
    private final Floor destinationFloor;

    public Passenger(Floor startFloor, Floor destinationFloor) {
        this.startFloor = startFloor;
        this.destinationFloor = destinationFloor;
    }

    public Floor getSTART_FLOOR() {
        return startFloor;
    }

    public Floor getDESTINATION_FLOOR() {
        return destinationFloor;
    }

    //Handler
    public void handleElevatorArrival(Elevator elevator) {
        planEnterElevatorEvent(elevator);
    }

    public void handleArrivalAtFloor(Floor currentFloor) {

    }

    //Planer
    private void planEnterElevatorEvent(Elevator elevator) {

    }

    private void planLeaveElevatorEvent(Floor currentFloor) {

    }

}
