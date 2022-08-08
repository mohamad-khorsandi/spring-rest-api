package ir.sobhan.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BadInputException extends Exception{
    public BadInputException() {
        super("ENTITY IS UNPROCESSABLE");
    }
}
@ControllerAdvice
class BadInputAdvice {
    @ResponseBody
    @ExceptionHandler(BadInputException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String BadInputHandler(BadInputException e){
        return e.getMessage();
    }
}