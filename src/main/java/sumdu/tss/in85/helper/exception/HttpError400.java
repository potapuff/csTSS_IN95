package sumdu.tss.in85.helper.exception;

public class HttpError400 extends HttpException {

    public HttpError400(String message) {
        super(message);
    }

    public Integer getCode() {
        return 400;
    }

}
