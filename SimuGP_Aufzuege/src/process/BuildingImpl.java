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

import controller.GlobalVariables;
import helper.RandomGenerator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author micmeist
 */
public class BuildingImpl implements Building {

    private final Elevator elevator;
    private final List<Floor> floors;

    public BuildingImpl(int numberOfFloors, int elevatorStartFloor) {
        floors = new ArrayList();
        createFloors(numberOfFloors);
        elevator = new ElevatorImpl(GlobalVariables.ELEVATOR_CAPACITY, GlobalVariables.ELEVATOR_SPEED, floors.get(elevatorStartFloor), 4);
    }

    private void createFloors(int numberOfFloors) {
        floors.add(new GroundFloor(this));
        for (int i = 1; i < numberOfFloors; i++) {
            floors.add(new TopFloor(i, this));
        }
    }

    private List<Floor> getFloorsWithPassengersOn() {
        List<Floor> result = new ArrayList();
        for (Floor floor : floors) {
            if (floor.hasPassengersOn()) {
                result.add(floor);
            }
        }

        return result;
    }

    @Override
    public Floor getFloor(int index) {
        return floors.get(index);
    }

    @Override
    public Floor getRandomFloor( AbstractFloor floor) {
        Floor result;
        do {
            result = floors.get(RandomGenerator.getInstance().getInt(0, floors.size() - 1));
        } while (floor.equals(result));
        return result;
    }

    @Override
    public Floor getRandomFloorWithPassengers() {
        List<Floor> floorsWithPassengersOn = getFloorsWithPassengersOn();
        Floor result;

        if (floorsWithPassengersOn.size() <= 1) {
            result = floors.get(0);
        } else {
            result = floorsWithPassengersOn.get(RandomGenerator.getInstance().getInt(0, floorsWithPassengersOn.size() - 1));
        }

        return result;
    }

    @Override
    public Elevator getElevator() {
        return elevator;
    }

}
