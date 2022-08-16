package ir.sobhan.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CanNotConvertDTOException extends RuntimeException {
    public CanNotConvertDTOException(Throwable cause) {
        super(cause);
    }
}

@ControllerAdvice
class CanNotConvertDTOAdvice {
    @ResponseBody
    @ExceptionHandler(CanNotConvertDTOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String AccessDeniedHandler() {
        return "some thing went wrong on server side. sorry :) ";
    }
}