package sumdu.tss.in95;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import sumdu.tss.in95.controller.MainController;
import sumdu.tss.in95.helper.Keys;
import sumdu.tss.in95.helper.ViewHelper;
import sumdu.tss.in95.helper.exception.HttpException;
import io.javalin.rendering.template.JavalinVelocity;

import java.io.File;

public class Server {

        private final Javalin app;

        {
            JavalinVelocity.init(null);
            app = Javalin.create(
                            config -> config.staticFiles.add("/public", Location.CLASSPATH))
                    .exception(HttpException.class, ViewHelper::userError)
                    .exception(Exception.class, (e, ctx) -> ViewHelper.userError(new HttpException(e), ctx))
                    .get("/", MainController::index)
                    .get("/{table}", MainController::show);
        }

    public static void main(final String[] args) {
        var file = new File(args.length < 1 ? "config.properties" : args[0]);
        Keys.loadParams(file);

        new Server().start(Integer.parseInt(Keys.get("APP.PORT")));
    }

    /**
     * Return url to site root, including protocol, host and port
     *
     * @return String like 'http://127.0.0.1:7000/'
     */
    @SuppressWarnings("HttpUrlsUsage")
    public String getBaseUrl() {
        return "http://" + Keys.get("APP.URL") + ":" + Keys.get("APP.PORT") + "/";
    }

    public void start(final int port) {
        this.app.start(port);
    }

    @SuppressWarnings("unused")
    public void stop() {
        this.app.stop();
    }

}
