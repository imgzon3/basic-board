package com.test.board.api.dto.result;

import lombok.Getter;

@Getter
public class ResultResponse {

    private final int status;
    private final String code;
    private final String message;
    private final Object data;

    private ResultResponse(ResultCode resultCode, Object data) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static ResultResponse of(ResultCode resultCode, Object data) {
        return new ResultResponse(resultCode, data);
    }
}
