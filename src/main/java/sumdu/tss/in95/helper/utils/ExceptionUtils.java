package sumdu.tss.in95.helper.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {

    private final static CharSequence NOT_FOUND_TEXT = "SQL error or missing database (no such table:";

    public static boolean isSQLTableNotFound(Throwable ex) {
        if (ex.getMessage() != null && ex.getMessage().contains(NOT_FOUND_TEXT)) {
            return true;
        }
        var cause = ex.getCause();
        return (cause != null) && isSQLTableNotFound(cause);
    }

    public static String stacktrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

}
