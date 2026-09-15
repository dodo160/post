package com.posts.exception;

public class EntityExistsException extends RuntimeException {
    public EntityExistsException() {
    }

    public EntityExistsException(String message) {
        super(message);
    }
}
