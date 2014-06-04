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
import events.PassengerEvent;
import events.PassengerMoving;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This singleton class provides an list of events and methods to insert specific types 
 * of events into the list. This list also provides methods to get and remove
 * always the first events from the list.
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

    /**
     * Returns the always the same instance of <code>FutureEventList</code> or creats one,
     * if not created before.
     * 
     * @return instance of <code>FutureEventList</code>
     */
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
            logger.log(Level.FINEST, "{0} Event added at first position", eventToAdd.getClass().getSimpleName());
        } else {
            for (Event event : eventList) {
                if (eventToAdd.getEventTime() > event.getEventTime()) {
                    index++;
                } else {
                    break;
                }
            }
            eventList.add(index, eventToAdd);
            logger.log(Level.FINEST, "{0} Event added at position {1}", new Object[]{eventToAdd.getClass().getSimpleName(), index});
        }

    }

    /**
     * Adds an event of the type <code>PassengerEvent</code> to the list of events.
     * If the event is not an instance of <code>PassengerArrival</code> the event
     * will be added bevor the <code>ElevatorStartMoving</code> event in the list
     * of events if exsisting.
     * 
     * @param passengerEvent - event to be insert into the list of events
     */
    public void addPassengerEvent(PassengerEvent passengerEvent) {
        logger.entering(this.getClass().getName(), "addPassengerEvent");
        if ((passengerEvent instanceof PassengerArrival)) {
            addPassengerArrivalEvent((PassengerArrival) passengerEvent);
        } else {
            addPassengerMovingEvent(passengerEvent);
        }
    }

    private void addPassengerArrivalEvent(PassengerArrival event) {
        logger.entering(this.getClass().getName(), "addPassengerArrivalEvent");
        addEvent(event);
    }

    private void addPassengerMovingEvent(PassengerEvent passengerEvent) {
        logger.entering(this.getClass().getName(), "addPassengerLeavedOrEnteredEvent");
        addEventBevoreStartMovingEvent(passengerEvent);
    }

    private void addEventBevoreStartMovingEvent(Event eventToAdd) {
        ElevatorStartMoving elevatorStartMovingEvent = null;
        for (Event event : eventList) {
            if (event instanceof ElevatorStartMoving) {
                elevatorStartMovingEvent = (ElevatorStartMoving) event;
                eventList.remove(event);
                break;
            }
        }
        addEvent(eventToAdd);
        if (elevatorStartMovingEvent != null) {
            addElevatorStartMovingEvent(elevatorStartMovingEvent);
        }
    }

    /**
     * Adds an envent of the type <code>ElevatorStartMoving</code> to the list of 
     * events if there are no other events of this type. If there are events of 
     * the type <code>PassengerMoving</code> or <code>ElevatorArrival</code> the
     * event will be insert right after them.
     *
     * @param elevatorStartMovingEvent - event to be insert into the list of events
     */
    public void addElevatorStartMovingEvent(ElevatorStartMoving elevatorStartMovingEvent) {
        logger.entering(this.getClass().getName(), "addElevatorStartMovingEvent");
        boolean add = true;
        int index = 0;
        //Only one elevatorStartMovingEvent allowed
        for (Event event : eventList) {
            if (event instanceof ElevatorStartMoving) {
                add = false;
                break;
            }
        }
        if (add) {
            for (Event event : eventList) {
                //elevatorStartMovingEvent is starting right after the last PassengerMoving or ElevatorArrival event
                if (event instanceof PassengerMoving
                        || event instanceof ElevatorArrival) {
                    elevatorStartMovingEvent.setEventTime(event.getEventTime());
                    index = eventList.indexOf(event) + 1;
                }
            }
            eventList.add(index, elevatorStartMovingEvent);
            logger.log(Level.FINEST, "ElevatorStartMoving Event added at position {0}", index);
        } else {
            logger.finest("ElevatorStartMoving event not added! Event of this Class allready exsisting in FutureEventList.");
        }

    }

    /**
     * Adds an event of the type <code>ElevatorArrival</code> to the list of events
     * bevor the <code>ElevatorStartMoving</code> event if exsisting.
     *
     * @param elevatorArrival - event to be insert into the list of events
     */
    public void addElevatorArrivalEvent(ElevatorArrival elevatorArrival) {
        logger.entering(this.getClass().getName(), "addElevatorArrivalEvent");
        addEventBevoreStartMovingEvent(elevatorArrival);
    }

    /**
     * Retrieves, but does not remove, the first element of the list of events, 
     * or returns null if this list is empty.
     *
     * @return the head of this list, or null if this list is empty
     */
    public Event peekNextEvent() {
        return eventList.peekFirst();
    }

    /**
     * Retrieves and removes the first element of the list of events, 
     * or returns null if this list is empty.
     * 
     * @return the first element of this list, or null if this list is empty
     */
    public Event pollNextEvent() {
        return eventList.pollFirst();
    }
}
