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

package controller;

/**
 * This class is recieving statistic data of the simulation
 *
 * @author micmeist
 */
public class Statistics {

    private Statistics() {
    }
    
    private static double maxWaitingTime;

    /**
     * Calculates the time a pessenger has to wait for an elevator in seconds 
     * and saving if the calculated time is higher then the value saved before
     *
     * @param start - the current simulation time in seconds when the passenger began to wait for an elevator
     * @param end
     */
    public static void passengerWaitingTime(double start, double end){
        double waitingTime = end - start;
        if(waitingTime > maxWaitingTime){
            maxWaitingTime = waitingTime;
        }
    }

    /**
     *
     * @return highest calculated time a pessenger has to wait for an elevator in seconds
     */
    public static double getMaxWaitingTime() {
        return maxWaitingTime;
    }
    
    
    
}
