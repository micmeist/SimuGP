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

import process.Passenger;

/**
 *
 * @author micmeist
 */
public abstract class PassengerEvent extends Event{
    
    protected final Passenger passenger;

    /**
     *
     * @param passenger the process of this event
     * @param timeUntilEventOccures the time in seconds between the current simulatuion time
     * (when the event is planed) and the simulation time when the event occures.
     */
    public PassengerEvent(Passenger passenger, double timeUntilEventOccures) {
        super(timeUntilEventOccures);
        this.passenger = passenger;
    }

    /**
     *
     * @return proccess of this event
     */
    protected Passenger getPassenger() {
        return passenger;
    }

    

}
