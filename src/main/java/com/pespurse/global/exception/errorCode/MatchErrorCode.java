package com.pespurse.global.exception.errorCode;

import com.pespurse.global.response.ResponseCode;

public enum MatchErrorCode implements ResponseCode {
    MATCH_ALREADY_CREATED("2004", "User is already registered.Please login"),
    USER_NOT_REGISTERED("2005", "Not a registered user.Please register first"),
    USER_NOT_FOUND("2006", "User not found.Please register");

    private String code;
    private String message;

    private MatchErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return null;
    }

    @Override
    public String message() {
        return null;
    }
}
