package com.test.board.api.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //Common
    INTERNAL_SERVER_ERROR(500, "C000", "Internal server error."),
    INVALID_INPUT_VALUE(400, "C001", "Invalid input value."),
    REQUEST_PARAMETER_MISSING(400, "C002", "Request Parameter is missing."),
    HTTP_MESSAGE_NOT_READABLE(400, "C003", "HTTP request body is invalid."),
    METHOD_NOT_ALLOWED(405, "C004", "HTTP method is not allowed"),

    //Post

    //Member
    USERID_ALREADY_EXIST(400, "M000", "User id already exist."),
    PASSWORD_NOT_EQUAL(400, "M001", "Password and password check is not equal.")

    ;

    private final int status;
    private final String code;
    private final String message;
}
