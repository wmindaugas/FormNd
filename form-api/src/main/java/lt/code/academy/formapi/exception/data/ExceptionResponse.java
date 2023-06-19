package lt.code.academy.formapi.exception.data;
@Getter
public class ExceptionResponse {
    private final String message;
    private final int status;
    private final long timestamp;
    private final String reason;

    public ExceptionResponse(String message, HttpStatus status) {
        this(message, status, null);
    }

    public ExceptionResponse(String message, HttpStatus status, String reason) {
        this.message = message;
        this.status = status.value();
        this.reason = reason;

        timestamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
    }
}
