package io.project.application.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.project.application.annotation.Verticle;

@Verticle
public class SecondVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        EventBus eb = vertx.eventBus();

        // Send a message every second
        //  vertx.setPeriodic(5000, v -> eb.publish("news-feed", "Some news!"));
    }

}
