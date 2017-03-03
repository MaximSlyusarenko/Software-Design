package ru.ifmo.ctddev.slyusarenko.sd.lab5.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
public class TaskList {
    private int id;
    private String name;
    private List<Task> tasks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
