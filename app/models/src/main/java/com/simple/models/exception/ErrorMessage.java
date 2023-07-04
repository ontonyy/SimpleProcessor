package com.simple.models.exception;

import java.util.Date;

public class ErrorMessage {
    public Integer statusCode;
    public String date;
    public String message;
    public String description;

    public ErrorMessage(final Integer statusCode, final String message, final String description) {
        this.statusCode = statusCode;
        this.date = new Date().toString();
        this.message = message;
        this.description = description;
    }
}
