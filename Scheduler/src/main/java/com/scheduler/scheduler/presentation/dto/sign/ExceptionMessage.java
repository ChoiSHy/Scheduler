package com.scheduler.scheduler.presentation.dto.sign;

public class ExceptionMessage {
    String error_type;
    String code;
    String message;

    public ExceptionMessage(String error_type, String code, String message) {
        this.error_type = error_type;
        this.code = code;
        this.message = message;
    }
}
