package ru.ifmo.ctddev.slyusarenko.sd.lab5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.dao.TaskDao;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.dao.TaskListDao;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.Task;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.TaskList;
import ru.ifmo.ctddev.slyusarenko.sd.term2.lab1.Profileble;

import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
@Controller
public class ProductController {
    @Autowired
    private TaskListDao taskListDao;
    @Autowired
    private TaskDao taskDao;

    @RequestMapping(value = "/add-task-list", method = RequestMethod.POST)
    @Profileble
    public String addTaskList(@ModelAttribute("taskList") TaskList taskList) {
        taskListDao.addTaskList(taskList);
        return "redirect:/get-task-lists";
    }

    @RequestMapping(value = "/get-task-lists", method = RequestMethod.GET)
    @Profileble
    public String getTaskLists(ModelMap map) {
        prepareModelMap(map, taskListDao.getTaskLists());
        return "index";
    }

    @RequestMapping(value = "/add-task", method = RequestMethod.POST)
    @Profileble
    public String addTask(@ModelAttribute("task") Task task) {
        taskDao.addTask(task);
        return "redirect:/get-task-lists";
    }

    @RequestMapping(value = "/update-task", method = RequestMethod.POST)
    @Profileble
    public String updateTask(@RequestParam String action, @ModelAttribute("updateTask") Task task) {
        if ("Change Status".equals(action)) {
            taskDao.changeStatus(task);
        } else if ("Remove Task".equals(action)) {
            taskDao.removeTask(task);
        }
        return "redirect:/get-task-lists";
    }

    private void prepareModelMap(ModelMap map, List<TaskList> lists) {
        map.addAttribute("lists", lists);
        map.addAttribute("taskList", new TaskList());
        map.addAttribute("task", new Task());
        map.addAttribute("updateTask", new Task());
    }
}
