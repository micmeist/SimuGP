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

import events.ElevatorStartMoving;
import events.Event;
import events.PassengerArrival;
import events.PassengerEntered;
import events.PassengerEvent;
import events.PassengerLeaved;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author micmeist
 */
public class FutureEventList {

    private static final Logger logger = Logger.getLogger(FutureEventList.class.getName());

    private final LinkedList<Event> eventList;
    private static FutureEventList instance;

    private FutureEventList() {
        this.eventList = new LinkedList();
    }

    public static FutureEventList getInstance() {
        if (instance == null) {
            instance = new FutureEventList();
        }
        return instance;
    }

    private void addEvent(Event eventToAdd) {
        int index = 0;
        if (eventList.isEmpty()) {
            eventList.add(eventToAdd);
            logger.info("Fist Event added");
        } else {
            for (Event event : eventList) {
                if (eventToAdd.getEventTime() < event.getEventTime()) {
                    eventList.add(index, eventToAdd);
                    logger.log(Level.INFO, "Event added at position {0}", index);
                    break;
                }
                index++;
            }
        }

    }

    public void addPassengerEvent(PassengerEvent passengerEvent) {
        if (passengerEvent.getClass().getName().equals(PassengerArrival.class.getName())) {
            addPassengerArrivalEvent((PassengerArrival) passengerEvent);
        } else {
            addPassengerLeavedOrEnteredEvent(passengerEvent);
        }
    }

    private void addPassengerArrivalEvent(PassengerArrival event) {
        addEvent(event);
    }

    private void addPassengerLeavedOrEnteredEvent(PassengerEvent passengerEvent) {
        ElevatorStartMoving elevatorStartMovingEvent = null;
        for (Event event : eventList) {
            if (ElevatorStartMoving.class.getName().equals(event.getClass().getName())) {
                elevatorStartMovingEvent = (ElevatorStartMoving) event;
                eventList.remove(event);
            }
        }
        addEvent(passengerEvent);
        if (elevatorStartMovingEvent != null) {
            addElevatorStartMovingEvent(elevatorStartMovingEvent);
        }
    }

    public void addElevatorStartMovingEvent(ElevatorStartMoving elevatorStartMovingEvent) {
        Integer index = null;
        for (Event event : eventList) {
            //elevatorStartMovingEvent is starting right after the PassengerLeaved or PassengerEntered event
            if (PassengerLeaved.class.getName().equals(event.getClass().getName())
                    || PassengerEntered.class.getName().equals(event.getClass().getName())) {
                index = eventList.indexOf(event);
                elevatorStartMovingEvent.setEventTime(event.getEventTime());
                break;
            }
        }
        if (index == null) {
            addEvent(elevatorStartMovingEvent);
        } else {
            eventList.add(index, elevatorStartMovingEvent);
        }
    }

    public Event peekNextEvent() {
        return eventList.peekFirst();
    }

    public Event peekNextEvent(String className) {
        Event result = null;
        for (Event event : eventList) {
            if (className.equals(event.getClass().getName())) {
                result = event;
                break;
            }
        }
        return result;
    }

    public Event pollNextEvent() {
        return eventList.pollFirst();
    }

    public Event pollNextEvent(String className) {
        Event event = peekNextEvent(className);
        if (event != null) {
            eventList.remove(event);
        }
        return event;
    }

}
