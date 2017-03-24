package ru.ifmo.ctddev.slyusarenko.sd.term2.lab4;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Maxim Slyusarenko
 * @since 24.03.17
 */
public class EventStatisticImplTest {

    @Test
    public void complexTest() {
        Instant firstNow = Instant.now();
        SetableClock clock = new SetableClock(firstNow);
        EventStatistic eventStatistic = new EventStatisticImpl(clock);
        eventStatistic.incEvent("First");
        Instant secondNow = firstNow.plus(100, ChronoUnit.SECONDS);
        clock.setNow(secondNow);
        eventStatistic.incEvent("Second");
        Instant thirdNow = secondNow.plus(27, ChronoUnit.SECONDS);
        clock.setNow(thirdNow);
        Map<Integer, List<Event>> result = eventStatistic.getAllEventStatistic();
        Assert.assertTrue(result.containsKey(2));
        Assert.assertEquals(result.get(2).size(), 1);
        Assert.assertEquals(result.get(2).get(0).getName(), "First");
        Assert.assertTrue(result.containsKey(0));
        Assert.assertEquals(result.get(0).size(), 1);
        Assert.assertEquals(result.get(0).get(0).getName(), "Second");
        Instant fourthNow = firstNow.plus(61, ChronoUnit.MINUTES);
        clock.setNow(fourthNow);
        result = eventStatistic.getAllEventStatistic();
        Assert.assertTrue(result.containsKey(59));
        Assert.assertEquals(result.get(59).size(), 1);
        Assert.assertEquals(result.get(59).get(0).getName(), "Second");
        List<Integer> minutes = new ArrayList<>();
        for (Integer minute : result.keySet()) {
            minutes.add(minute);
        }
        Assert.assertEquals(minutes.size(), 1);
        Assert.assertEquals((long) minutes.get(0), (long) 59);
    }
}
