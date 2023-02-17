package sumdu.tss.in95.helper;

import io.javalin.http.Context;
import sumdu.tss.in95.helper.exception.HttpException;
import sumdu.tss.in95.helper.utils.ExceptionUtils;

import java.util.HashMap;

public final class ViewHelper {

    public static void userError(HttpException exception, final Context context) {
        String stacktrace = ExceptionUtils.stacktrace(exception);
        userError(context, exception.getCode(), exception.getMessage(), exception.getIcon(), stacktrace);
    }

    public static void userError(final Context context, final Integer code,
                                 final String message, final String icon, final String stacktrace) {
        var model = new HashMap<String, Object>();
        model.put("code", code.toString());
        model.put("message", message);
        model.put("icon", icon);
        if (!Keys.isProduction()) {
            model.put("stacktrace", stacktrace);
        }
        context.status(code);
        context.render("/velocity/error.vm", model);
    }

}
