package com.example.Plowithme.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonResponse<T> {


    private int code;
    private String message;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }
    public CommonResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
