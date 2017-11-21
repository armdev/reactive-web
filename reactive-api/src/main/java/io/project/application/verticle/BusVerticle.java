package io.project.application.verticle;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.Vertx;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class BusVerticle {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private Vertx vertx;

    @PostConstruct
    public void init() {
        //ola la
        vertx.setPeriodic(9000, v -> eventBus.publish("info", "Time is " + System.currentTimeMillis()));

        eventBus.consumer("info", message -> System.out.println("Rrecived:::::: " + message.body()));
    }

}
