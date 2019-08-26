package com.md.md.case1;

public class MarsRoverException extends RuntimeException{
    private ErrorCode errorCode;

    public MarsRoverException(ErrorCode errorCode, String message) {
        super(errorCode + " - " + message);
        this.errorCode = errorCode;
    }

    public MarsRoverException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode + " - " +message, cause);
        this.errorCode = errorCode;
    }
}
