package com.demo.restaurant_management.web.dto.response.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    protected String message;
    protected Object data;

    public static Response of (String message, Object data) {
        return Response.builder()
                .message(message)
                .data(data)
                .build();
    }

}
