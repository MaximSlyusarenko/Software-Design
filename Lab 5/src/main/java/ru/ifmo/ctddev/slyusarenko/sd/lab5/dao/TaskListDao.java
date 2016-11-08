package ru.ifmo.ctddev.slyusarenko.sd.lab5.dao;

import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.TaskList;

import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
public interface TaskListDao {

    String addTaskList(TaskList taskList);

    List<TaskList> getTaskLists();
}
