package ir.sobhan.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AccessDeniedException extends Exception{
    public AccessDeniedException(String message) {
        super(message);
    }
}

@ControllerAdvice
class AccessDeniedAdvice {
    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String AccessDeniedHandler(AccessDeniedException e){
        return e.getMessage();
    }
}