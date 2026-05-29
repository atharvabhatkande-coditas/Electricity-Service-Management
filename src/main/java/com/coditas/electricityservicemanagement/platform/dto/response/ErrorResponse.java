package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private LocalDateTime localDateTime;
    private Integer statusCode;

    public ErrorResponse(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}