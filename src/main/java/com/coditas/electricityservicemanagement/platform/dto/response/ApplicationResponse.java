package com.coditas.electricityservicemanagement.platform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationResponse <T>{
    private T data;
    private List<ErrorResponse> errorResponseList;


    public ApplicationResponse(List<ErrorResponse> errorResponseList) {
        this.errorResponseList = errorResponseList;
    }

    public ApplicationResponse(T data) {
        this.data = data;
    }
}
