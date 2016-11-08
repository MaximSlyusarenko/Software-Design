package ru.ifmo.ctddev.slyusarenko.sd.lab5.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
@Getter
@Setter
public class Task {
    private int id;
    private String name;
    private boolean done;
}
