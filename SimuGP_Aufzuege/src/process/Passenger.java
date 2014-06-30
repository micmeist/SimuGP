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
 * This interface represents the passenger process in the simulation.
 *
 * @author micmeist
 */
public interface Passenger {

    /**
     *
     * @return
     */
    Floor getDESTINATION_FLOOR();

    /**
     *
     * @return
     */
    Floor getSTART_FLOOR();

    /**
     * Returns the simulation time of the arrvial of this pessenger. This is the
     * same moment when the passenger starts waiting for an elevator.
     *
     * @return simulation time when the passengers starts waiting for an elevator
     */
    double getWaitingStartTime();

    /**
     * Handler called by <code>Elevator</code> to activate the passenger process when the elevator
     * arrived at the destination.
     *
     * @param floor
     * @param elevator
     */
    void handleArrivalAtFloor(Floor floor, Elevator elevator);

    //Handler

    /**
     * Handler called by <code>Floor</code> to activate the passenger process when
     * an elevator arrived at the floor on which the passenger is waiting for the
     * elevator.
     *
     * @param elevator
     */
    void handleElevatorArrival(Elevator elevator);
    
    /**
     * Handler for the <code>PassengerEntered</code> event
     *
     * @param elevator elevator to enter
     */
    void handlePassengerEntered(Elevator elevator);

    /**
     * Handler for the <code>PassengerLeaved</code> event
     *
     * @param elevator elevator to leave
     */
    void handlePassengerLeaved(Elevator elevator);

    /**
     * Set the simulation time when the passenger starts waiting for an elevator.
     *
     * @param waitingStartTime
     */
    void setWaitingStartTime(double waitingStartTime);
    
}
