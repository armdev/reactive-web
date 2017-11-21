package io.project.application.config;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "io.project")
public class ApplicationContext {

    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

    @Bean
    public EventBus eventBus() {
        return vertx().eventBus();
    }
}
