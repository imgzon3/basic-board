package com.test.board.api.advice;

import com.test.board.api.dto.error.ErrorCode;
import com.test.board.api.dto.error.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * <b>GET object(@ModelAttribute)</b>, <b>POST(@RequestBody)</b> 관련된 <b>@Valid</b> 데이터 검증 실패한 경우
     */
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, this.getErrors(e.getBindingResult()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * <b>GET parameter(@RequestParam, @PathVariable)</b> 관련된 <b>@Valid</b> 데이터 검증 실패한 경우
     *
     * @return FieldError에 field값은 예외가 발생한 parameter의 위치를 반환한다.
     * ex) "field": "parameter 0"
     */
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        log.error("HandlerMethodValidationException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, this.getErrors(e.getAllValidationResults()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * <b>@Validated</b>에 의해 발생하는 예외 처리
     * <b>@Controller</b>가 아닌 곳에서 처리한 예외 발생할 경우
     */
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("ConstraintViolationException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, this.getErrors(e.getConstraintViolations()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * handleMethodArgumentNotValidException()가 처리하지 못한 BindException 있을 경우
     */
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error("BindException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, this.getErrors(e.getBindingResult()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * <b>@RequestParm</b> 혹은 <b>@RequestPart</b>의 필수 파라미터가 결여된 경우
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, MissingServletRequestPartException.class})
    protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(Exception e) {
        log.error("MissingServletRequestParameter/PartException", e);
        final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of(this.getRequestParam(e), "", ErrorCode.REQUEST_PARAMETER_MISSING.getMessage());
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * enum type 일치하지 않을 경우
     * 주로 @RequestParam enum에 binding 못할 경우 발생
     */
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException", e);
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of(e.getName(), value, e.getErrorCode());
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * <b>@RequestBody</b> 형식과 불일치하거나, HTTP API인데 <b>JSON</b> 형식에 맞지 않을 경우
     */
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.HTTP_MESSAGE_NOT_READABLE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 지원하지 않는 <b>HTTP method</b> 호출할 경우
     */
    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 그 외 모든 예외 처리
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleException", e);
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<ErrorResponse.FieldError> getErrors(final BindingResult bindingResult) {
        final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.stream()
                .map(error -> new ErrorResponse.FieldError(
                        error.getField(),
                        error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                        error.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    private List<ErrorResponse.FieldError> getErrors(final Set<ConstraintViolation<?>> constraintViolations) {
        final List<ConstraintViolation<?>> lists = new ArrayList<>(constraintViolations);
        return lists.stream()
                .map(error -> new ErrorResponse.FieldError(
                        error.getPropertyPath() == null ? "" : error.getPropertyPath().toString(),
                        error.getInvalidValue() == null ? "" : error.getInvalidValue().toString(),
                        error.getMessage()
                )).collect(Collectors.toList());
    }

    private List<ErrorResponse.FieldError> getErrors(final List<ParameterValidationResult> parameterValidationResults) {
        return parameterValidationResults.stream()
                .map(error -> new ErrorResponse.FieldError(
                        "parameter ".concat(String.valueOf(error.getMethodParameter().getParameterIndex())),
                        error.getArgument() == null ? "" : error.getArgument().toString(),
                        error.getResolvableErrors().get(0).getDefaultMessage()
                )).collect(Collectors.toList());
    }

    private String getRequestParam(Exception e) {
        if (e instanceof MissingServletRequestParameterException) {
            return ((MissingServletRequestParameterException) e).getParameterName();
        } else {
            return ((MissingServletRequestPartException) e).getRequestPartName();
        }
    }

}
