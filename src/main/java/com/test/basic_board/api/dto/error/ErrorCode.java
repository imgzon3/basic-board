package com.test.basic_board.api.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //Common
    INTERNAL_SERVER_ERROR(500, "C000", "Internal Server Error"),
    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
    REQUEST_PARAMETER_MISSING(400, "C002", "Request Parameter Missing"),
    HTTP_MESSAGE_NOT_READABLE(400, "C003", "HTTP Request body is invalid"),
    METHOD_NOT_ALLOWED(400, "C004", "Method Not Allowed")

    //member


    //post

    ;

    private final int status;
    private final String code;
    private final String message;

}
