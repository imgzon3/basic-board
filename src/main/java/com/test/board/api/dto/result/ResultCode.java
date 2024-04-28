package com.test.board.api.dto.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    
    //Member
    SIGNUP_SUCCESS(200, "M100", "Signup Success."),
    LOGIN_SUCCESS(200, "M101", "Login Success."),
    ;
    
    private final int status;
    private final String code;
    private final String message;
}
