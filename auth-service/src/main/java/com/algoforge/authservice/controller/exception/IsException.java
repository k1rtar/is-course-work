package com.algoforge.authservice.controller.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IsException extends RuntimeException {
    
    private String message;

    public IsException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
