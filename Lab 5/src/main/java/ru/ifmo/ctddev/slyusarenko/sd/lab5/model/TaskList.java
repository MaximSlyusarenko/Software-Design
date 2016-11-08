package ru.ifmo.ctddev.slyusarenko.sd.lab5.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
@Getter
@Setter
public class TaskList {

    private String name;
    private List<Task> tasks;
}
