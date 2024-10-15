package com.hnm.userexpecienceservice.exception;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(Long userId) {
        super("User id "+ userId+ " not found");
    }
}
