package com.demo.restaurant_management.web.exception.handle;

import com.demo.restaurant_management.web.dto.response.utils.ErrorResponse;
import com.demo.restaurant_management.web.dto.response.utils.Response;
import com.demo.restaurant_management.web.dto.response.utils.ResponseUtils;
import com.demo.restaurant_management.web.exception.EntityNotFoundException;
import com.demo.restaurant_management.web.exception.ServiceException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response> unknownErrorHandler(Exception e) {
        log.error("Unexpected Exception", e);
        String errKey = "err.sys.unexpected-exception";
        String errMsg = "Unknown error";
        return ResponseUtils.internalErr(ErrorResponse.of(errKey, errMsg));
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<Response> ServiceErrorHandler(ServiceException e) {
        String errKey = e.getMessage();
        String errMsg = e.getErrMsg();
        log.error(errKey, errMsg);
        return ResponseUtils.badRequest(ErrorResponse.of(errKey, errMsg));
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Response> EntityNotFoundErrorHandler(EntityNotFoundException e) {
        String errKey = e.getMessage();
        String errMsg = "Can not find " + e.getEntityName() + " with id: " + e.getEntityId();
        log.error(errKey, errMsg);
        return ResponseUtils.badRequest(ErrorResponse.of(errKey, errMsg));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Response> FormatErrorHandler(HttpMessageNotReadableException exception) {
        String errMsg = "Input format error";
        if (exception.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ifx = (InvalidFormatException) exception.getCause();
            if (ifx.getTargetType() != null && ifx.getTargetType().isEnum()) {
                errMsg = String.format("Invalid enum value: '%s' for the field: '%s'. The value must be one of: %s.",
                        ifx.getValue(), ifx.getPath().get(ifx.getPath().size() - 1).getFieldName(), Arrays.toString(ifx.getTargetType().getEnumConstants()));
            }
        }
        String errKey = "err.sys.invalid-input";
        log.error(errKey, errMsg);
        return ResponseUtils.badRequest(ErrorResponse.of(errKey, errMsg));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return ResponseUtils.badRequest(ErrorResponse
                .of("err.invalid-request", "invalid request", getErrorsMap(errors)));
    }

    private Map<String, Object> getErrorsMap(List<String> errors) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
