package com.test.board.api.dto.error;

import lombok.Getter;


import java.util.List;


@Getter
public class ErrorResponse {

    private final String message;
    private final int status;
    private final String code;
    private List<FieldError> fieldErrors;


    private ErrorResponse(final ErrorCode code, final String message, final List<FieldError> fieldErrors) {
        this.message = message;
        this.status = code.getStatus();
        this.code = code.getCode();
        this.fieldErrors = fieldErrors;
    }

    private ErrorResponse(final ErrorCode code, final List<FieldError> fieldErrors) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.fieldErrors = fieldErrors;
    }

    private ErrorResponse(final ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }

    public static ErrorResponse of(final ErrorCode code, final List<FieldError> fieldErrors) {
        return new ErrorResponse(code, fieldErrors);
    }

    public static ErrorResponse of(final ErrorCode code, final String message, final List<FieldError> fieldErrors) {
        return new ErrorResponse(code, message, fieldErrors);
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

}
