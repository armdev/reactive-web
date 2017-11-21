package io.project.application.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import io.project.application.annotation.Verticle;
import org.springframework.beans.factory.annotation.Value;

@Verticle
public class HttpServerVerticle extends AbstractVerticle {

    private static final int DEFAULT_HTTP_PORT = 8080;

    private final int httpPort;

    @Autowired
    HttpServerVerticle(@Value("${server.port}") final int httpPort) {
        this.httpPort = getHttpPort(httpPort);
    }

    private int getHttpPort(final int httpPort) {
        if (httpPort < 0) {
            return DEFAULT_HTTP_PORT;
        }
        return httpPort;
    }

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        router.get("/health").handler(ctx -> {
            ctx.response().end("I'm ok, I hope you are also ok" + System.currentTimeMillis());
        });

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(7000, serverStartHandler(startFuture));
    }

    private Handler<HttpServerRequest> requestHandler() {
        return onRequest -> onRequest.response().end("It works!");
    }

    private Handler<AsyncResult<HttpServer>> serverStartHandler(final Future<Void> startFuture) {
        return onComplete -> {
            if (onComplete.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(onComplete.cause());
                System.exit(0);
            }
        };
    }
}
