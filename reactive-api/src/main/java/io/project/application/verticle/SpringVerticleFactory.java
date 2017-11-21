package io.project.application.verticle;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.spi.VerticleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpringVerticleFactory implements VerticleFactory {

    private final ApplicationContext applicationContext;
    private final Vertx vertx;

    @Autowired
    public SpringVerticleFactory(final ApplicationContext applicationContext, final Vertx vertx) {
        this.applicationContext = applicationContext;
        this.vertx = vertx;
    }

    @PostConstruct
    public void init() {
        vertx.registerVerticleFactory(this);
    }

    @Override
    public boolean blockingCreate() {
        return true;
    }

    @Override
    public String prefix() {
        return "spring-verticle-factory";
    }

    @Override
    public Verticle createVerticle(final String verticleName, final ClassLoader classLoader) throws Exception {
        final String clazz = VerticleFactory.removePrefix(verticleName);
        return (Verticle) applicationContext.getBean(Class.forName(clazz));
    }

}
