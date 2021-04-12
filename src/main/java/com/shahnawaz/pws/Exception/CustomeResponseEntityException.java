package com.shahnawaz.pws.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomeResponseEntityException extends ResponseEntityExceptionHandler {
    public CustomeResponseEntityException() {
        super();
    }
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object>
    handleAllException(Exception ex, WebRequest request) throws Exception {

        ExceptionResponse er=new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity(er, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<Object> handleCustomException(Exception ex,WebRequest req){
        ExceptionResponse er=new ExceptionResponse(new Date(),ex.getMessage(),req.getDescription(false));
        return new ResponseEntity(er,HttpStatus.BAD_REQUEST);
    }

}
