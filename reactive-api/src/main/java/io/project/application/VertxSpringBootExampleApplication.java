package io.project.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.project.application.verticle.DeploymentManager;

import javax.annotation.PostConstruct;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Configuration
@EnableCaching
@EnableAsync
//@EnableEurekaClient
//@EnableHystrix
@EnableCircuitBreaker
//@EnableDiscoveryClient
public class VertxSpringBootExampleApplication {

    public static void main(final String[] args) {
        SpringApplication.run(VertxSpringBootExampleApplication.class, args);
    }

    private final DeploymentManager deploymentManager;

    @Autowired
    public VertxSpringBootExampleApplication(final DeploymentManager deploymentManager) {
        this.deploymentManager = deploymentManager;
    }

    @PostConstruct
    public void deployVerticles() {
        deploymentManager.deployVerticles();
    }

}
