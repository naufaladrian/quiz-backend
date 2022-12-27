package com.app.quizz.exception;

import com.app.quizz.response.CommonResponseGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException notFoundException){
        return CommonResponseGenerator.errorResponse(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException businessException){
        return CommonResponseGenerator.errorResponse(businessException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleUserPass(BadCredentialsException badCredentialsException){
        return CommonResponseGenerator.errorResponse(badCredentialsException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
