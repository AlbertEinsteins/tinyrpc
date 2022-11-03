package com.tinyrpc.core.exception;


public class BaseException extends Exception {
    private String message;

    public BaseException(String message) {
        this.message = message;
    }
}
