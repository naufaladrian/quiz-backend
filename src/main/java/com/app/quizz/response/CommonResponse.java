package com.app.quizz.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonResponse<T> {
    @JsonProperty
    private int status;
    @JsonProperty
    private String message;
    @JsonProperty
    private T data;

    public CommonResponse() {
    }

    public CommonResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
