package com.pespurse.users.repo.entity.enums;

import com.pespurse.global.exception.SystemFailureException;
import com.pespurse.global.exception.errorCode.SystemErrorCode;

import java.util.Arrays;

public enum UserType {
    BEGINNER(1),AMATEUR(2),PROFESSIONAL(3),SUPERSTAR(4),TOP_PLAYER(5),LEGEND(6);

    private int userTypeCode;

    UserType(int userTypeCode) {
        this.userTypeCode = userTypeCode;
    }

    public UserType getUserTypeByCode(int userTypeCode) {
        return Arrays.stream(values()).filter(value -> value.userTypeCode == userTypeCode).findFirst().orElseThrow(() -> new SystemFailureException(SystemErrorCode.SOMETHING_WENT_WRONG));
    }

    private int getUserTypeCode() {
        return this.userTypeCode;
    }
}
