package com.api.task.exceptions;

import java.util.List;

public class ApiBusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final List<String> messages;

    private final String userMessage;

    public ApiBusinessException(final String message, final Throwable cause) {
        super(message, cause);
        this.messages = null;
        this.userMessage = message;
    }

    public ApiBusinessException(final String message) {
        super(message, null);
        this.messages = null;
        this.userMessage = message;
    }

    public ApiBusinessException(final Throwable cause) {
        super(null, cause);
        this.messages = null;
        this.userMessage = null;
    }

    public ApiBusinessException() {
        super(null, null);
        this.messages = null;
        this.userMessage = null;
    }

    public ApiBusinessException(final String userMessage, final String mensagemExcecao, final Throwable cause) {
        super(mensagemExcecao, cause);
        this.messages = null;
        this.userMessage = userMessage;
    }

    public ApiBusinessException(final List<String> messages) {
        super();
        this.messages = messages;
        this.userMessage = null;
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public String getUserMessage() {
        return this.userMessage;
    }
}