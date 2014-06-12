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
 * This interface represents the elevator process in the simulation.
 * 
 * @author micmeist
 */
public interface Elevator {

    /**
     *
     * @return the current floor on wich the elevator is located at the moment
     */
    Floor getCurrentFloor();

    /**
     *
     * @return the number of passengers which are in the elevator at the moment
     */
    int getCurrentNumberOfPassengers();

    /**
     *
     * @return the destination where the elevator is moving at the moment or null,
     * if the elevator is not moving
     */
    Floor getDestination();

    /**
     * Returns the number of passengers transported by the elevator. This is not
     * regarding the single passenger as a person. For example: A person taking 
     * the elevator to move up, leaving the elevator and taking the elevator to 
     * move down again is rated as two transported pessengers.
     * 
     * This method should be used for the simulation statistics only!
     *
     * @return the counted number of passengers transported by the elevator
     */
    int getNumberOfPassengersTransported();

    /**
     * Returns the number of of situations the capacity of the elevator was reached
     * but there were still at least one passenger who wants to enter the 
     * elevator.
     * 
     * This method should be used for the simulation statistics only!
     *
     * @return the counted number of situations the capacity was reached
     */
    int getNumberOfSituationsCapacityReached();

    //Handler

    /**
     * Handler for the <code>ElevatorArrival</code> event
     *
     * @param floor floor on wich the elevator arrives
     */
    void handleArrival(Floor floor);

    /**
     * Handler for the call of a floor
     *
     * @param floor the calling floor
     */
    void handleCall(Floor floor);

    /**
     * Handler for the <code>PassengerEntered</code> event
     *
     * @param passenger passenger who entered the elevator
     */
    void handlePassengerEntered(Passenger passenger);

    /**
     * Handler for the <code>PassengerLeaved</code> event
     *
     * @param passenger passenger who leaved the elevator
     */
    void handlePassengerLeaved(Passenger passenger);

    /**
     * Handler for the <code>ElevatorStartMoving</code> event
     *
     * @param floor the floor the elevator starts moving to
     */
    void handleStartMoving(Floor floor);

    /**
     * 
     * @param callingFloor
     */
    void setCallingFloor(Floor callingFloor);

    /**
     *
     * @param currentState
     */
    void setState(AbstractElevatorState currentState);
    
    /**
     * Plans the next <code>ElevatorArrival</code> event for this process.
     *
     * @param floor
     */
    void planElevatorArrival(Floor floor);
    
    /**
     * Plans the next <code>ElevatorStartMoving</code> event for this process.
     *
     * @param floorToMoveTo
     */
    void planStartMoving(Floor floorToMoveTo);
    
}
