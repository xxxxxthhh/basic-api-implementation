package com.thoughtworks.rslist.Exception;


public class StartEndException extends RuntimeException {

    private String message;

    public StartEndException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}


