package com.pespurse.global.exception.errorCode;

import com.pespurse.global.response.ResponseCode;

public enum TeamErrorCode implements ResponseCode {
    TEAM_NOT_FOUND("3004", "No player found"),
    TEAM_ALREADY_SAVED("3001", "Player already saved"),
    PLAYER_ALREADY_ADDED_TO_TEAM("4004", "No player found");


    private String code;
    private String message;
    private TeamErrorCode(String code, String message){
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
