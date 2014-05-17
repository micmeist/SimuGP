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

import helper.RandomGenerator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author micmeist
 */
public class Building {

    private final Elevator elevator;
    private final List<AbstractFloor> floors;

    public Building(int numberOfFloors, int elevatorStartFloor) {
        floors = new ArrayList();
        createFloors(numberOfFloors);
        elevator = new Elevator(10, 5, floors.get(elevatorStartFloor), 4);
    }

    private void createFloors(int numberOfFloors) {
        floors.add(new GroundFloor(this));
        for (int i = 1; i < numberOfFloors; i++) {
            floors.add(new Floor(i, this));
        }
    }

    private List<AbstractFloor> getFloorsWithPassengersOn() {
        List<AbstractFloor> result = new ArrayList();
        for (AbstractFloor floor : floors) {
            if (floor.hasPassengersOn()) {
                result.add(floor);
            }
        }

        return result;
    }

    public AbstractFloor getFloor(int index) {
        return floors.get(index);
    }

    public AbstractFloor getRandomFloor( AbstractFloor floor) {
        AbstractFloor result;
        do {
            result = floors.get(RandomGenerator.getInstance().getInt(0, floors.size() - 1));
        } while (floor.equals(result));
        return result;
    }

    public AbstractFloor getRandomFloorWithPassengers() {
        List<AbstractFloor> floorsWithPassengersOn = getFloorsWithPassengersOn();
        AbstractFloor result;

        if (floorsWithPassengersOn.size() <= 1) {
            result = floors.get(0);
        } else {
            result = floorsWithPassengersOn.get(RandomGenerator.getInstance().getInt(0, floorsWithPassengersOn.size() - 1));
        }

        return result;
    }

    public Elevator getElevator() {
        return elevator;
    }

}
