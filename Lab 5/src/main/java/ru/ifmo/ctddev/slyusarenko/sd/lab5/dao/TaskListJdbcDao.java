package ru.ifmo.ctddev.slyusarenko.sd.lab5.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.Task;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.TaskList;
import ru.ifmo.ctddev.slyusarenko.sd.term2.lab1.Profileble;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
public class TaskListJdbcDao extends JdbcDaoSupport implements TaskListDao {

    @Autowired
    private TaskDao taskDao;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS LISTS" +
            " (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, NAME TEXT NOT NULL)";

    public TaskListJdbcDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    @Profileble
    public String addTaskList(TaskList taskList) {
        String sql = "INSERT INTO LISTS (NAME) VALUES (?)";
        getJdbcTemplate().update(sql, new Object[] { taskList.getName() });
        return taskList.getName();
    }

    @Override
    @Profileble
    public List<TaskList> getTaskLists() {
        getJdbcTemplate().execute(CREATE_TABLE);
        String sql = "SELECT * FROM LISTS";
        List<TaskList> taskLists = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(TaskList.class));
        for (TaskList taskList : taskLists) {
            taskList.setTasks(taskDao.getTasksForList(taskList));
        }
        return taskLists;
    }
}
