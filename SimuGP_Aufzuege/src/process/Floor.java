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
import events.PassengerArrival;
import java.util.ArrayDeque;

/**
 *
 * @author micmeist
 */
public class Floor implements Process {

    private final ArrayDeque<Passenger> passangers;
    private final int floorNumber;
    private final Building building;

    public Floor(int floorNumber, Building building) {
        this.floorNumber = floorNumber;
        this.passangers = new ArrayDeque();
        this.building = building;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
    
    public int getCurrentNumberOfPassengers(){
        return passangers.size();
    }
    
    public void removePassenger(Passenger passenger){
        passangers.remove(passenger);
    }

    //Handler
    public void handlePassengerArrival(Passenger passenger) {
        passangers.add(passenger);
        planPassengerArrival();
        callElevator();
    }

    public void handleElevatorArrival() {
        notifyPassengers();
    }

    //Notifier
    private void notifyPassengers() {
        if (!passangers.isEmpty()) {
            passangers.getFirst().handleElevatorArrival(building.getElevator());
        }
    }

    private void callElevator() {
        building.getElevator().handleCall(this);
    }

    //Planer
    private void planPassengerArrival() {
        FutureEventList.getInstance().addPassengerEvent(new PassengerArrival(this, building.getRandomFloor(this)));
    }

}
