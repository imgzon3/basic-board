package com.test.board.api.exception;

import com.test.board.api.dto.error.ErrorCode;
import com.test.board.api.dto.error.ErrorResponse;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final List<ErrorResponse.FieldError> errors;

    public BusinessException(String message, ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
        super(message);
        this.errorCode = errorCode;
        this.errors = errors;
    }

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.errors = new ArrayList<>();
    }

    public BusinessException(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errors = errors;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errors = new ArrayList<>();
    }

}
