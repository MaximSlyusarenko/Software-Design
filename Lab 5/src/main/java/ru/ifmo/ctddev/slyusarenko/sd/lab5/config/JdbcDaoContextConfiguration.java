package ru.ifmo.ctddev.slyusarenko.sd.lab5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.dao.TaskJdbcDao;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.dao.TaskListJdbcDao;

import javax.sql.DataSource;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
@Configuration
public class JdbcDaoContextConfiguration {
    @Bean
    public TaskListJdbcDao taskListJdbcDao(DataSource dataSource) {
        return new TaskListJdbcDao(dataSource);
    }

    @Bean
    public TaskJdbcDao taskJdbcDao(DataSource dataSource) {
        return new TaskJdbcDao(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:lists.db");
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }
}
