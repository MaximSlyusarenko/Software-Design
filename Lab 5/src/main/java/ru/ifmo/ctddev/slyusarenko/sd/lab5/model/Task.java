package ru.ifmo.ctddev.slyusarenko.sd.lab5.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
public class Task {
    private int id;
    private String name;
    private boolean done;
    private int listId;

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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }
}
