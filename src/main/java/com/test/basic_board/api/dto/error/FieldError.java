package com.test.basic_board.api.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class FieldError {
    private final String field;
    private final String value;
    private final String reason;

    public static List<FieldError> of(final String field, final String value, final String reason) {
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError(field, value, reason));
        return fieldErrors;
    }
}
