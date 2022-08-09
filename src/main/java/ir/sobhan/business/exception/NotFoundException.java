package ir.sobhan.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotFoundException extends Exception {
    public NotFoundException(String entity, Object spec) {
        super("there is no " + entity + " with this Specification:" + spec);
    }

    public NotFoundException(Object spec) {
        this("entity", spec);
    }
}

@ControllerAdvice
class EntityNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String entityNotFoundHandler(NotFoundException e) {
        return e.getMessage();
    }
}