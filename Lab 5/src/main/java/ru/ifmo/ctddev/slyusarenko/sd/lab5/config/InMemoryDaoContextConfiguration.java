package ru.ifmo.ctddev.slyusarenko.sd.lab5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.dao.TaskListDao;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.dao.TaskListInMemoryDao;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public TaskListDao taskListDao() {
        return new TaskListInMemoryDao();
    }
}
