package com.test.skybettest.Exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int id) {
        super("Could not find user " + id);
    }
}
