package com.pespurse.players.repo.entity.enums;

import com.pespurse.global.exception.SystemFailureException;
import com.pespurse.global.exception.errorCode.SystemErrorCode;

import java.util.Arrays;

public enum Position {
    CF(1),SS(2),RWF(3),LWF(4),AMF(5),RMF(6),LMF(7),CMF(8),DMF(9),RB(10),LB(11),CB(12),GK(13);

    private int positionCode;
    Position(int positionCode) {
        this.positionCode = positionCode;
    }

    public static Position getEnumByPositionCode(int positionCode) {
       return Arrays.stream(values()).filter(value -> value.positionCode == positionCode).findFirst().orElseThrow(() -> new SystemFailureException(SystemErrorCode.SOMETHING_WENT_WRONG));
    }

    public int getPositionCode() {
        return this.positionCode;
    }

}
