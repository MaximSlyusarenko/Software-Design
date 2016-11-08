package ru.ifmo.ctddev.slyusarenko.sd.lab5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.dao.TaskListDao;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.TaskList;

import java.util.List;
import java.util.Optional;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
@Controller
public class ProductController {
    @Autowired
    private TaskListDao taskListDao;

    @RequestMapping(value = "/add-task-list", method = RequestMethod.POST)
    public String addTaskList(@ModelAttribute("taskList") TaskList taskList) {
        taskListDao.addTaskList(taskList);
        return "redirect:/get-task-lists";
    }

    @RequestMapping(value = "/get-task-lists", method = RequestMethod.GET)
    public String getTaskLists(ModelMap map) {
        prepareModelMap(map, taskListDao.getTaskLists());
        return "index";
    }

    private void prepareModelMap(ModelMap map, List<TaskList> lists) {
        map.addAttribute("lists", lists);
        map.addAttribute("taskList", new TaskList());
    }
}
