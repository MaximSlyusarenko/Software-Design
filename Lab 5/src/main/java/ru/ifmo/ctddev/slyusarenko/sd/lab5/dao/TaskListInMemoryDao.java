package ru.ifmo.ctddev.slyusarenko.sd.lab5.dao;

import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.TaskList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
public class TaskListInMemoryDao implements TaskListDao {

    private final List<TaskList> taskLists = new CopyOnWriteArrayList<>();

    @Override
    public String addTaskList(TaskList taskList) {
        taskLists.add(taskList);
        return taskList.getName();
    }

    @Override
    public List<TaskList> getTaskLists() {
        return new ArrayList(taskLists);
    }
}
