package com.thoughtworks.demo.handler.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException() {
        super("Game not found!");
    }
}
