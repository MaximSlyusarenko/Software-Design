package ru.ifmo.ctddev.slyusarenko.sd.lab5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.dao.ProductDao;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.dao.ProductInMemoryDao;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public ProductDao productDao() {
        return new ProductInMemoryDao();
    }
}
