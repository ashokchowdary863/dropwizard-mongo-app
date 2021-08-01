package com.ashok.exception;

import org.eclipse.jetty.http.HttpStatus;

public enum AppError {
    BAD_REQUEST(HttpStatus.BAD_REQUEST_400, "Bad request"),
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR_500, "Something went wrong..."),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST_400, "User already exists..."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST_400, "User not found....");
    private final int code;
    private final String message;

    private AppError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "MyError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
