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

package events;

import process.ElevatorImpl;
import process.Floor;

/**
 *
 * @author micmeist
 */
public class ElevatorStartMoving extends ElevatorEvent{
    
    /**
     *
     */
    public final Floor floorToMoveTo;

    /**
     *
     * @param elevator process of this event
     * @param floorToMoveTo the floor the elevator has to move to
     */
    public ElevatorStartMoving(ElevatorImpl elevator, Floor floorToMoveTo) {
        //Elevator start moving immediately
        super(elevator, 0.0);
        this.floorToMoveTo = floorToMoveTo;
    }

    /**
     *
     * @return the floor the elevator has to move to
     */
    public Floor getFloorToMoveTo() {
        return floorToMoveTo;
    }
 
    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        getElevator().handleStartMoving(floorToMoveTo);
    }
    
}
