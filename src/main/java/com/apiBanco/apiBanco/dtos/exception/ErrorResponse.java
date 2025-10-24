package com.apiBanco.apiBanco.dtos.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter
public class ErrorResponse {
    private LocalDateTime timestap;
    private int status;
    private String error;
    private String message;

    public ErrorResponse (LocalDateTime timestap, int status, String error , String message){
        this.timestap = timestap;
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
