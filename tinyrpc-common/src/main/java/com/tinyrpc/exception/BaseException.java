package com.tinyrpc.exception;


public class BaseException extends Exception {
    private String message;

    public BaseException(String message) {
        this.message = message;
    }
}
