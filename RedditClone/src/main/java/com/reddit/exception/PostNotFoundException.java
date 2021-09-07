package com.reddit.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String postId) {
        super(postId);
    }
}
