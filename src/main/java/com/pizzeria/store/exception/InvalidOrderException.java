package com.pizzeria.store.exception;

public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException(String msg) {
        super(msg);
    }
}
