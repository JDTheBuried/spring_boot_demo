package com.thoughtworks.demo.handler;

import com.thoughtworks.demo.handler.exception.GameNotFoundException;
import com.thoughtworks.demo.handler.exception.GameTypeNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResult handle(GameNotFoundException exception) {
        System.out.println(exception.getMessage());
        return new ErrorResult(exception.getMessage());
    }

    @ExceptionHandler(GameTypeNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handle(GameTypeNotMatchException exception) {
        return new ErrorResult(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handle(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(";"));
        return new ErrorResult(message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResult handle(Exception exception) {
        return new ErrorResult(exception.getMessage());
    }
}
