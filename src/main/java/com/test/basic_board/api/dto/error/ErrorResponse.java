package com.test.basic_board.api.dto.error;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponse {

    private final int status;
    private final String code;
    private final String message;
    private List<FieldError> fieldErrors;

    private ErrorResponse(final ErrorCode errorCode, final List<FieldError> fieldErrors) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.fieldErrors = fieldErrors;
    }

    private ErrorResponse(final ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ErrorResponse of (final ErrorCode errorCode, final List<FieldError> fieldErrors) {
        return new ErrorResponse(errorCode, fieldErrors);
    }

    public static ErrorResponse of (final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

}
