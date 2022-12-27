package com.app.quizz.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonResponseGenerator {

    public static <T> CommonResponse<T> successResponse(T data){
        CommonResponse<T> response = new CommonResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.name());
        response.setData(data);
        return response;
    }

    public static <T> ResponseEntity<CommonResponse<T>> errorResponse(String message, HttpStatus httpStatus){
        CommonResponse<T> response = new CommonResponse<>();
        response.setStatus(httpStatus.value());
        response.setMessage(httpStatus.name());
        response.setData((T) message);
        return new ResponseEntity<>(response,httpStatus);
    }

}
