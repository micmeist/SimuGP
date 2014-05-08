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

import events.ElevatorArrival;
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
        logger.entering(this.getClass().getName(), "addEvent");
        int index = 0;
        if (eventList.isEmpty()) {
            eventList.add(eventToAdd);
            logger.log(Level.INFO, "{0} Event added at first position", eventToAdd.getClass().getSimpleName());
        } else {
            for (Event event : eventList) {
                if (eventToAdd.getEventTime() > event.getEventTime()) {
                    index++;
                } else {
                    break;
                }
            }
            eventList.add(index, eventToAdd);
            logger.log(Level.INFO, "{0} Event added at position {1}", new Object[]{eventToAdd.getClass().getSimpleName(), index});
        }

    }

    public void addPassengerEvent(PassengerEvent passengerEvent) {
        logger.entering(this.getClass().getName(), "addPassengerEvent");
        if (passengerEvent.getClass().getName().equals(PassengerArrival.class.getName())) {
            addPassengerArrivalEvent((PassengerArrival) passengerEvent);
        } else {
            addPassengerLeavedOrEnteredEvent(passengerEvent);
        }
    }

    private void addPassengerArrivalEvent(PassengerArrival event) {
        logger.entering(this.getClass().getName(), "addPassengerArrivalEvent");
        addEvent(event);
    }

    private void addPassengerLeavedOrEnteredEvent(PassengerEvent passengerEvent) {
        logger.entering(this.getClass().getName(), "addPassengerLeavedOrEnteredEvent");
        ElevatorStartMoving elevatorStartMovingEvent = null;
        for (Event event : eventList) {
            if (ElevatorStartMoving.class.getName().equals(event.getClass().getName())) {
                elevatorStartMovingEvent = (ElevatorStartMoving) event;
                eventList.remove(event);
                break;
            }
        }
        addEvent(passengerEvent);
        if (elevatorStartMovingEvent != null) {
            addElevatorStartMovingEvent(elevatorStartMovingEvent);
        }
    }

    public void addElevatorStartMovingEvent(ElevatorStartMoving elevatorStartMovingEvent) {
        logger.entering(this.getClass().getName(), "addElevatorStartMovingEvent");
        boolean add = true;
        int index = 0;
        for (Event event : eventList) {
            if (ElevatorStartMoving.class.getName().equals(event.getClass().getName())) {
                ElevatorStartMoving currentEvent = (ElevatorStartMoving) event;
                if (elevatorStartMovingEvent.getFloorToMoveTo().getFloorNumber() == currentEvent.getFloorToMoveTo().getFloorNumber()) {
                    add = false;
                    break;
                }
            }
        }
        if (add) {
            for (Event event : eventList) {
                //elevatorStartMovingEvent is starting right after the last PassengerLeaved, PassengerEntered or ElevatorArrival event
                if (PassengerLeaved.class.getName().equals(event.getClass().getName())
                        || PassengerEntered.class.getName().equals(event.getClass().getName())
                        || ElevatorArrival.class.getName().equals(event.getClass().getName())
                        || ElevatorStartMoving.class.getName().equals(event.getClass().getName())) {
                    elevatorStartMovingEvent.setEventTime(event.getEventTime());
                    index = eventList.indexOf(event) + 1;
                }
            }
            eventList.add(index, elevatorStartMovingEvent);
        } else {
            logger.warning("ElevatorStartMoving event not added! Event of this Class and for this floor allready exsisting in FutureEventList.");
        }

    }

    public void addElevatorArrivalEvent(ElevatorArrival elevatorArrival) {
        logger.entering(this.getClass().getName(), "addElevatorArrivalEvent");
        addEvent(elevatorArrival);
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
