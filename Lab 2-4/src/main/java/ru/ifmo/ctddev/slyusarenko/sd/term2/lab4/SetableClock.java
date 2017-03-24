package ru.ifmo.ctddev.slyusarenko.sd.term2.lab4;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * @author Maxim Slyusarenko
 * @since 24.03.17
 */
@AllArgsConstructor
public class SetableClock implements Clock {

    @Setter
    private Instant now;

    @Override
    public Instant now() {
        return now;
    }
}
