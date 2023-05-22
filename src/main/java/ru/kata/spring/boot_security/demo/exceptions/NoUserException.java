package ru.kata.spring.boot_security.demo.exceptions;

public class NoUserException extends RuntimeException {
    public NoUserException(String message) {
        super(message);
    }
}
