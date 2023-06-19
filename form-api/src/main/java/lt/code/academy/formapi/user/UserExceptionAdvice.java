package lt.code.academy.formapi.user;

import lt.code.academy.formapi.exception.data.ExceptionResponse;
import lt.code.academy.formapi.user.exception.InvalidUserNameException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(basePackages = "lt.code.academy.eshopapi.user")
public class UserExceptionAdvice {

    @ExceptionHandler(InvalidUserNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleInvalidUserNameException(InvalidUserNameException ex) {

        return new ExceptionResponse("Invalid username", HttpStatus.BAD_REQUEST, ex.getReason());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleUserValidationException(MethodArgumentNotValidException ex) {
        //TODO handle exceptions
        return new ExceptionResponse("Validation exception", HttpStatus.BAD_REQUEST);
    }
}
