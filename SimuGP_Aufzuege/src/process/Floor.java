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

/**
 * This interface represents the floor process in the simulation.
 *
 * @author micmeist
 */
public interface Floor {

    /**
     * Adding one to the counter for pessangers on the floor. This is not
     * creating an instance of <code>Passenger</code>
     */
    void addPassengerOnFloor();

    /**
     *
     * @return number of passengers waiting for an elevator
     */
    int getCurrentNumberOfPassengersInQueue();

    /**
     *
     * @return number of the floor beginning with 0 for the ground floor
     */
    int getFloorNumber();

    /**
     * Handler called by <code>Elevator</code> to activate the floor process when the elevator
     * arrived at this floor.
     */
    void handleElevatorArrival();

    //Handler
    /**
     * Handler for the <code>PassengerArrival</code> event.
     * 
     * @param passenger the passenger who arrives at the floor
     */
    void handlePassengerArrival(Passenger passenger);

    /**
     * Check if there are passengers on the floor not waiting for an elevator.
     * 
     * @return true if there are passengers on the floor not waiting for an elevator
     */
    boolean hasPassengersOn();

    /**
     * Check if the current instance is an instance of <code>GroundFloor</code>
     *
     * @return true if the current instance is an instance of <code>GroundFloor</code>
     */
    boolean isGroundFloor();
    
    /**
     * Remove an instance of <code>Passenger</code> from the queue of pessengers
     * waiting for an elevator
     * 
     * @param passenger the instance to remove
     */
    void removePassengerFromQueue(Passenger passenger);

}
