package com.pespurse.global.exception.errorCode;

import com.pespurse.global.response.ResponseCode;

public enum PlayerErrorCode implements ResponseCode {
    PLAYER_NOT_FOUND("1004", "No player found"),
    PLAYER_ALREADY_SAVED("1001", "Player already saved");

    private String code;
    private String message;
    private PlayerErrorCode(String code, String message){
        this.code = code;
        this.message = message;
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
