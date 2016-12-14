package ru.ifmo.ctddev.slyusarenko.sd.lab5.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.Task;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.TaskList;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 14.12.16
 */
public class TaskJdbcDao extends JdbcDaoSupport implements TaskDao {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS TASKS" +
            " (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, NAME TEXT NOT NULL, DONE BOOLEAN, LISTID INTEGER NOT NULL, " +
            "FOREIGN KEY (LISTID) REFERENCES LISTS(ID))";

    public TaskJdbcDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    public void addTask(Task task) {
        String sql = "INSERT INTO TASKS (NAME, DONE, LISTID) VALUES (?, ?, ?)";
        getJdbcTemplate().update(sql, new Object[] { task.getName(), false, task.getListId() });
    }

    @Override
    public void changeStatus(Task task) {
        String sql = "UPDATE TASKS SET DONE = NOT DONE WHERE ID = ? AND LISTID = ?";
        getJdbcTemplate().update(sql, new Object[] { task.getId(), task.getListId() });
    }

    @Override
    public void removeTask(Task task) {
        String sql = "DELETE FROM TASKS WHERE ID = ?";
        getJdbcTemplate().update(sql, new Object[] { task.getId() });
    }

    @Override
    public List<Task> getTasksForList(TaskList taskList) {
        getJdbcTemplate().execute(CREATE_TABLE);
        String sql = "SELECT T.ID, T.NAME, T.DONE FROM TASKS AS T INNER JOIN LISTS AS L ON T.LISTID = L.ID WHERE L.ID = " + taskList.getId();
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(Task.class));
    }
}
