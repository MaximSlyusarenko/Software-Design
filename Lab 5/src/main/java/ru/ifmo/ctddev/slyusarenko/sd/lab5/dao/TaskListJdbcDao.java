package ru.ifmo.ctddev.slyusarenko.sd.lab5.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.TaskList;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
public class TaskListJdbcDao extends JdbcDaoSupport implements TaskListDao {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS LISTS" +
            " (NAME TEXT PRIMARY KEY NOT NULL)";

    public TaskListJdbcDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    public String addTaskList(TaskList taskList) {
        String sql = "INSERT INTO LISTS (NAME) VALUES (?)";
        getJdbcTemplate().update(sql, new Object[] { taskList.getName() });
        return taskList.getName();
    }

    @Override
    public List<TaskList> getTaskLists() {
        getJdbcTemplate().execute(CREATE_TABLE);
        String sql = "SELECT * FROM LISTS";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(TaskList.class));
    }
}
