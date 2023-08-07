package com.pespurse.global.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Response<T> {
    private boolean success = true;
    private String code = "2001";
    private String message = "Request Success";
    private T data;

    public Response(){
        super();
    }

    public Response(T data) {
        this.data = data;
    }

    public Response(T data,ResponseCode responseCode) {
        this.data = data;
        this.code = responseCode.code();
        this.message = responseCode.message();
    }

    public Response(String code, String message) {
        this.success = false;
        this.code = code;
        this.message = message;
    }

    public static <T> Response<T> badRequest(ResponseCode responseCode) {
        return new Response<>(responseCode.code(), responseCode.message());
    }
}
