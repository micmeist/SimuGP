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

import java.util.ArrayDeque;

/**
 *
 * @author micmeist
 */
public abstract class AbstractFloor implements Floor {

    protected final ArrayDeque<Passenger> passangersInQueue = new ArrayDeque();
    protected final int floorNumber;
    protected final Building building;

    public AbstractFloor(int floorNumber, Building building) {
        this.floorNumber = floorNumber;
        this.building = building;
    }

    @Override
    public int getFloorNumber() {
        return floorNumber;
    }

    @Override
    public int getCurrentNumberOfPassengersInQueue() {
        return passangersInQueue.size();
    }

    @Override
    public void removePassengerFromQueue(Passenger passenger) {
        passangersInQueue.remove(passenger);
    }

    //Handler
    @Override
    public void handlePassengerArrival(Passenger passenger) {
        passangersInQueue.add(passenger);
        reducePassengersOnFloor();
        planPassengerArrival();
        callElevator();
    }

    @Override
    public void handleElevatorArrival() {
        notifyPassengers();
    }

    //Notifier
    private void notifyPassengers() {
        if (!passangersInQueue.isEmpty()) {
            passangersInQueue.getFirst().handleElevatorArrival(building.getElevator());
        } else {
            building.getElevator().setState(new StateWaitingEmpty(building.getElevator()));
        }
    }

    private void callElevator() {
        building.getElevator().handleCall(this);
    }

    //Planer
    protected abstract void planPassengerArrival();

}
