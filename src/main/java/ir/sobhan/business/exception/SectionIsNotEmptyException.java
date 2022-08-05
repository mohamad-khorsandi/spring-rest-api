package ir.sobhan.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SectionIsNotEmptyException extends Exception{
    public SectionIsNotEmptyException() {
        super("can not delete a non empty section");
    }
}

@ControllerAdvice
class SectionIsNotEmptyAdvice{
    @ResponseBody
    @ExceptionHandler(SectionIsNotEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String SectionIsNotEmptyHandler(SectionIsNotEmptyException e){
        return e.getMessage();
    }
}


