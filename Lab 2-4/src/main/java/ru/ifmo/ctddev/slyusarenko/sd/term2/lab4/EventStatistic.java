package ru.ifmo.ctddev.slyusarenko.sd.term2.lab4;

import java.util.List;
import java.util.Map;

/**
 * @author Maxim Slyusarenko
 * @since 24.03.17
 */
public interface EventStatistic {
    void incEvent(String name);
    Map<Integer, List<Event>> getEventStatisticByName(String name);
    Map<Integer, List<Event>> getAllEventStatistic();
    void printStatistic();
}
