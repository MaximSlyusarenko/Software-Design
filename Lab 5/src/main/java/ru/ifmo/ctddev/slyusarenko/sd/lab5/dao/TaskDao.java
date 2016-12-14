package ru.ifmo.ctddev.slyusarenko.sd.lab5.dao;

import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.Task;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.TaskList;

import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public interface TaskDao {

    List<Task> getTasksForList(TaskList taskList);

    void addTask(Task task);

    void changeStatus(Task task);

    void removeTask(Task task);
}
