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
import events.PassengerArrivalOnFloor;

/**
 *
 * @author micmeist
 */
public class TopFloor extends AbstractFloor {

    private int passangersOnFloor = 0;

    /**
     *
     * @param floorNumber number of floor in the building beginning with 1 for first floor over the ground floor
     * @param building
     */
    public TopFloor(int floorNumber, BuildingImpl building) {
        super(floorNumber, building);
    }

    /**
     * 
     * @return the number of passengers on the floor not waiting for an elevator
     */
    public int getNumberOfPassangersOnFloor() {
        return passangersOnFloor;
    }

    /**
     * {@inheritDoc}
     * 
     * @return always false because this class is not <code>GroundFloor</code>
     */
    @Override
    public boolean isGroundFloor() {
        return false;
    }

    /**
     * {@inheritDoc}
     * 
     * @return true if there are passengers on the floor not waiting for an elevator
     */
    @Override
    public boolean hasPassengersOn() {
        return passangersOnFloor > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPassengerOnFloor() {
        passangersOnFloor++;
        planPassengerArrival();        
    }
    
    /**
     * {@inheritDoc}
     * This method adds the arriving passengers to the queue of passengers 
     * waiting for an elevator, reducing the counter of passengers on floor, calls
     * the elevator and plan the next <code>PassengerArrival</code> event.
     * 
     * @param passenger the passenger who arrives at the floor
     */
    @Override
    public void handlePassengerArrival(Passenger passenger) {
        passangersInQueue.add(passenger);
        passangersOnFloor--;
        callElevator();
    }

    /**
     * {@inheritDoc}
     * 
     * This implementation is creating an <code>PassengerArrivalOnFloor</code> event.
     */
    @Override
    protected void planPassengerArrival() {
        if (hasPassengersOn()) {
            FutureEventList.getInstance().addPassengerEvent(new PassengerArrivalOnFloor(this, building.getFloor(0)));
        }
    }
}
