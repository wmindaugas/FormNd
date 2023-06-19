package lt.code.academy.formapi.user.exception;

public class InvalidUserNameException extends RuntimeException {
    private static final String REASON = "username.exist";

    public String getReason() {
        return REASON;
    }
}
