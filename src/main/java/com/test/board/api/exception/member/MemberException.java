package com.test.board.api.exception.member;

import com.test.board.api.dto.error.ErrorCode;
import com.test.board.api.dto.error.FieldError;
import com.test.board.api.exception.BusinessException;

import java.util.List;

public class MemberException extends BusinessException {

    public MemberException(ErrorCode errorCode, List<FieldError> errors) {
        super(errorCode, errors);
    }

    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
