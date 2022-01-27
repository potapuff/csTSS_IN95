package sumdu.tss.in85.helper.exception;

public class NotFoundException extends HttpError400 {

    public NotFoundException(String message) {
        super(message);
    }

    public String getIcon() {
        return "telescope";
    }

    public Integer getCode() {
        return 404;
    }

}
