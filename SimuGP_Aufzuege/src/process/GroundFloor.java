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
import events.PassengerArrivalOnGround;

/**
 *
 * @author micmeist
 */
public class GroundFloor extends AbstractFloor {

    /**
     *
     * @param building
     */
    public GroundFloor(BuildingImpl building) {
        super(0, building);
    }

    /**
     * {@inheritDoc}
     * @return always true because this class is <code>GroundFloor</code>
     */
    @Override
    public boolean isGroundFloor() {
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @return always true
     */
    @Override
    public boolean hasPassengersOn() {
        //return true because passengers coming into the building arrvie here
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPassengerOnFloor() {
        //Do nothing
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reducePassengersOnFloor() {
        //Do nothing
    }

    /**
     *{@inheritDoc}
     * 
     * This implementation is creating an <code>PassengerArrivalOnGround</code> event.
     */
    @Override
    protected void planPassengerArrival() {
        FutureEventList.getInstance().addPassengerEvent(new PassengerArrivalOnGround(this, building.getRandomFloor(this)));
    }

}
