package com.test.board.api.exception.member;

import com.test.board.api.dto.error.ErrorCode;
import com.test.board.api.dto.error.FieldError;

import java.util.List;

public class MemberSignUpException extends MemberException{

    public MemberSignUpException(ErrorCode errorCode, List<FieldError> errors) {
        super(errorCode, errors);
    }

    public MemberSignUpException(ErrorCode errorCode) {
        super(errorCode);
    }
}
