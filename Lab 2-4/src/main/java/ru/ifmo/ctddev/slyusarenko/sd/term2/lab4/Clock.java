package ru.ifmo.ctddev.slyusarenko.sd.term2.lab4;

import java.time.Instant;

/**
 * @author Maxim Slyusarenko
 * @since 24.03.17
 */
public interface Clock {
    Instant now();
}
