package ru.ifmo.ctddev.slyusarenko.sd.lab5.dao;

import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.Task;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.TaskList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class TaskInMemoryDao implements TaskDao {

    private final Map<Integer, List<Task>> listToTasks = new HashMap<>();

    @Override
    public void addTask(Task task) {
        listToTasks.putIfAbsent(task.getListId(), new ArrayList<>());
        listToTasks.get(task.getListId()).add(task);
    }

    @Override
    public void changeStatus(Task task) {
        List<Task> tasks = listToTasks.get(task.getListId());
        tasks.stream().filter(task1 -> task1.getId() == (task.getId())).forEach(task1 -> task1.setDone(!task1.isDone()));
    }

    @Override
    public void removeTask(Task task) {
        List<Task> tasks = listToTasks.get(task.getListId());
        List<Task> newTasks = new ArrayList<>();
        for (Task task1 : tasks) {
            if (!(task1.getId() == task.getId())) {
                newTasks.add(task1);
            }
        }
        listToTasks.put(task.getListId(), newTasks);
    }

    @Override
    public List<Task> getTasksForList(TaskList taskList) {
        return new ArrayList(listToTasks.get(taskList.getName()));
    }
}
