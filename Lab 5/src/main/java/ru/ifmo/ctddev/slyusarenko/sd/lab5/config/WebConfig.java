package ru.ifmo.ctddev.slyusarenko.sd.lab5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
@Configuration
@EnableWebMvc
@ComponentScan("ru.ifmo.ctddev.slyusarenko.sd.lab5.controller")
@Import({JdbcDaoContextConfiguration.class})
public class WebConfig {

    @Bean
    public InternalResourceViewResolver configureInternalResourceViewResolver() {
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();
        resolver.setPrefix("WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}
