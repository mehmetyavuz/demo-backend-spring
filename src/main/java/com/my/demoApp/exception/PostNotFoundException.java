package com.my.demoApp.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String s) {
        super(s);
    }
}
