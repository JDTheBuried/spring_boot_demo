package com.thoughtworks.demo.handler.exception;

public class GameTypeNotMatchException extends RuntimeException {
    public GameTypeNotMatchException() {
        super("Game type not match!");
    }
}
