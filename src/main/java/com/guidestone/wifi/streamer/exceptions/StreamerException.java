package com.guidestone.wifi.streamer.exceptions;


public class StreamerException extends RuntimeException {

    private ExceptionType type;

    public ExceptionType getType() {
        return type;
    }

    // method chaining
    public StreamerException setType(ExceptionType type) {
        this.type = type;
        return this;
    }

    public StreamerException(String message) {
        super(message);
    }

    public StreamerException(Throwable cause) {
        super(cause);
    }

    public StreamerException(String message, Throwable cause) {
        super(message, cause);
    }
}