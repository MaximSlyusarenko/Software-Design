package ru.ifmo.ctddev.slyusarenko.sd.term2.lab4;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

/**
 * @author Maxim Slyusarenko
 * @since 24.03.17
 */
@Getter
@Setter
@AllArgsConstructor
public class Event {
    private String name;
    private Instant time;

    @Override
    public String toString() {
        return "Event with name " + name;
    }
}
