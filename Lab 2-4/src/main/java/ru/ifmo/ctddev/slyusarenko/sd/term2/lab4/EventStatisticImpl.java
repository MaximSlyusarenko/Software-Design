package ru.ifmo.ctddev.slyusarenko.sd.term2.lab4;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Maxim Slyusarenko
 * @since 24.03.17
 */
public class EventStatisticImpl implements EventStatistic {

    private Clock clock;
    private Map<Instant, Event> events;

    public EventStatisticImpl(Clock clock) {
        this.clock = clock;
        this.events = new HashMap<>();
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void incEvent(String name) {
        Instant currentTime = clock.now();
        removeOldEvents(currentTime);
        events.put(currentTime, new Event(name, currentTime));
    }

    @Override
    public Map<Integer, List<Event>> getEventStatisticByName(String name) {
        Map<Integer, List<Event>> allEvents = getAllEventStatistic();
        Map<Integer, List<Event>> result = new HashMap<>();
        for (Map.Entry<Integer, List<Event>> entry : allEvents.entrySet()) {
            int minutes = entry.getKey();
            List<Event> events = entry.getValue();
            List<Event> goodEvents = new ArrayList<>();
            for (Event event : events) {
                if (event.getName().equals(name)) {
                    goodEvents.add(event);
                }
            }
            if (goodEvents.size() != 0) {
                result.put(minutes, goodEvents);
            }
        }
        return result;
    }

    @Override
    public Map<Integer, List<Event>> getAllEventStatistic() {
        Instant currentTime = clock.now();
        removeOldEvents(currentTime);
        Map<Integer, List<Event>> result = new HashMap<>();
        for (Map.Entry<Instant, Event> entry : events.entrySet()) {
            Instant eventTime = entry.getKey();
            long minutesBetween = getMinutesBetween(currentTime, eventTime);
            result.putIfAbsent((int) minutesBetween, new ArrayList<>());
            result.get((int) minutesBetween).add(entry.getValue());
        }
        return result;
    }

    @Override
    public void printStatistic() {
        Map<Integer, List<Event>> events = getAllEventStatistic();
        for (Map.Entry<Integer, List<Event>> entry : events.entrySet()) {
            System.out.println(entry.getKey() + " minutes ago was events: ");
            List<Event> minuteEvents = entry.getValue();
            for (Event minuteEvent : minuteEvents) {
                System.out.println(minuteEvent.toString());
            }
        }
    }

    private void removeOldEvents(Instant currentTime) {
        Map<Instant, Event> result = new HashMap<>();
        for (Map.Entry<Instant, Event> entry : events.entrySet()) {
            Instant eventTime = entry.getKey();
            if (getMinutesBetween(currentTime, eventTime) < 60) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        events = result;
    }

    private long getMinutesBetween(Instant currentTime, Instant eventTime) {
        long currentTimeSeconds = currentTime.getEpochSecond();
        long eventTimeSeconds = eventTime.getEpochSecond();
        return (currentTimeSeconds - eventTimeSeconds) / 60;
    }
}
