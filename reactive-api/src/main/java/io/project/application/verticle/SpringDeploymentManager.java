package io.project.application.verticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.spi.VerticleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import io.project.application.annotation.Verticle;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class SpringDeploymentManager implements DeploymentManager {

    private static final int DEFAULT_THREAD_POOL_SIZE = 10;

    private final ApplicationContext context;
    private final VerticleFactory verticleFactory;
    private final Vertx vertx;
    private final int threadPoolSize;

    @Autowired
    public SpringDeploymentManager(
            final ApplicationContext context,
            final VerticleFactory verticleFactory,
            final Vertx vertx,
            @Value("${thread.pool.size}") final int threadPoolSize
    ) {
        this.context = context;
        this.verticleFactory = verticleFactory;
        this.vertx = vertx;
        this.threadPoolSize = getThreadPoolSize(threadPoolSize);
    }

    private int getThreadPoolSize(final int threadPoolSize) {
        if (threadPoolSize <= 0) {
            return DEFAULT_THREAD_POOL_SIZE;
        }
        return threadPoolSize;
    }

    @Override
    public void deployVerticles() {
        final List<String> verticles = beansAnnotatedWith(Verticle.class);
        final DeploymentOptions options = defaultOptions();
        verticles.forEach(verticle -> deployWithOptions(verticle, options));
    }

    private List<String> beansAnnotatedWith(final Class<? extends Annotation> annotation) {

        final String factoryPrefix = verticleFactory.prefix();
        final Function<String, String> applyFactoryPrefix = name -> factoryPrefix + ":" + name;

        final String[] annotatedBeanNames = context.getBeanNamesForAnnotation(annotation);

        return Stream.of(annotatedBeanNames)
                .map(context::getBean)
                .map(Object::getClass)
                .map(Class::getName)
                .map(applyFactoryPrefix)
                .collect(toList());
    }

    private void deployWithOptions(final String verticle, final DeploymentOptions options) {
        vertx.deployVerticle(verticle, options);
    }

    private DeploymentOptions defaultOptions() {
        final DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setInstances(Runtime.getRuntime().availableProcessors());
        deploymentOptions.setWorkerPoolSize(threadPoolSize);
        return deploymentOptions;
    }

}
