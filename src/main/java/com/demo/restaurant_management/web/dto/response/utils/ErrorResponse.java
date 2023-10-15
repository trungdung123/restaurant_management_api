package com.demo.restaurant_management.web.dto.response.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse extends Response {

    private final String errKey;
    private final Map<String, Object> params;

    protected ErrorResponse(String errKey, String message, Map<String, Object> params) {
        this.errKey = errKey;
        this.message = message;
        this.params = params;
    }

    public static ErrorResponse of(String errKey) {
        return new ErrorResponse(errKey, null, null);
    }

    public static ErrorResponse of(String errKey, String details) {
        return new ErrorResponse(errKey, details, null);
    }

    public static ErrorResponse of(String errKey, String details, Map<String, Object> params) {
        return new ErrorResponse(errKey, details, params);
    }
}
