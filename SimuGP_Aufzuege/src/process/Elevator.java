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
 *
 * @author micmeist
 */
public interface Elevator {

    /**
     *
     * @return
     */
    Floor getCurrentFloor();

    /**
     *
     * @return
     */
    int getCurrentNumberOfPassengers();

    /**
     *
     * @return
     */
    Floor getDestination();

    /**
     *
     * @return
     */
    int getNumberOfPassengersTransported();

    /**
     *
     * @return
     */
    int getNumberOfSituationsCapacityReached();

    //Handler

    /**
     *
     * @param floor
     */
        void handleArrival(Floor floor);

    /**
     *
     * @param floor
     */
    void handleCall(Floor floor);

    /**
     *
     * @param passenger
     */
    void handlePassengerEntered(Passenger passenger);

    /**
     *
     * @param passenger
     */
    void handlePassengerLeaved(Passenger passenger);

    /**
     *
     * @param floor
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
     *
     * @param floor
     */
    void planElevatorArrival(Floor floor);
    
    /**
     *
     * @param floorToMoveTo
     */
    void planStartMoving(Floor floorToMoveTo);
    
}
