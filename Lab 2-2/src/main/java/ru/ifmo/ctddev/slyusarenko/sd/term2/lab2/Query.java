package ru.ifmo.ctddev.slyusarenko.sd.term2.lab2;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Maxim Slyusarenko
 * @since 09.03.17
 */
@AllArgsConstructor
@Getter
public class Query {
    private final String query;
    private final int queryNumber;
}
