package com.reddit.exception;

import org.springframework.mail.MailException;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String errorMessage) {
        super(errorMessage);
    }
}
