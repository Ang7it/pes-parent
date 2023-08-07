package com.pespurse.global.exception.errorCode;

import com.pespurse.global.response.ResponseCode;

public enum SystemErrorCode implements ResponseCode {
    SOMETHING_WENT_WRONG("5000", "Something went wrong in our end");
    String code;
    String message;

    private SystemErrorCode(String code, String message){
        this.code = code;
        this.message =  message;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }
}
