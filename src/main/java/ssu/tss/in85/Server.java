package ssu.tss.in85;

import io.javalin.Javalin;
import io.javalin.core.util.JavalinLogger;
import io.javalin.http.staticfiles.Location;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class Server {

        private final Javalin app;

        {
            app = Javalin.create(
                            config -> {
                                config.addStaticFiles("/public", Location.CLASSPATH);
                            })
                    .before(context -> JavalinLogger.info("[" + context.method() + "] " + context.url()));
            app.get("/", Server::index);
        }

        public static void index(Context context) {
           Map<String, Object> model = new HashMap<>();
           context.render("/velocity/index.vm", model);
        }

        public static void main(final String[] args) {
            new Server().start(7000);
        }


        public void start(final int port) {
            this.app.start(port);
        }

        public void stop() {
            this.app.stop();
        }

    }
