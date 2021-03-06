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
 *
 * @author micmeist
 */
public class GlobalVariables {

    /**
     * Hight of a single Floor in meters
     */
    public static final double FLOOR_HIGHT = 150;

    /**
     * Number of persons fit in the elevator
     */
    public static final int ELEVATOR_CAPACITY = 10;

    /**
     * Time the elevator needs to close or open the door in seconds
     */
    public static final double TIME_TO_MOVE_DOOR = 4;

    /**
     * Elevator speed in m/s
     */
    public static final double ELEVATOR_SPEED = 3;

    /**
     * Mean of passenger arrival time in seconds
     */
    public static final double PASSENGER_ARRIVAL_EVENT_TIME_MEAN = 30;

    /**
     * Mean of time passengers need to enter or leave elevator in seconds
     */
    public static final double PASSENGER_ENTER_AND_LEAVE_EVENT_TIME_MEAN = 2;

    /**
     * Minimum time passengers spending on floor in seconds
     */
        public static final int TIME_PASSENGER_SPEND_ON_FLOOR_MEAN = 600;

    /**
     * Maximum PLANED time passengers spending on floor in seconds
     */
        public static final int TIME_PASSENGER_SPEND_ON_FLOOR_DEVIATION = 300;
    
    /**
     * simulation end time in seconds
     */
    public static final double MAX_SIMULATION_TIME = 28800;

    /**
     * Simulation time in seconds
     */
    public static double simulationTime = 0;

}
