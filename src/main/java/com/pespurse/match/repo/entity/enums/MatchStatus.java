package com.pespurse.match.repo.entity.enums;

import com.pespurse.global.exception.SystemFailureException;
import com.pespurse.global.exception.errorCode.SystemErrorCode;

import java.util.Arrays;

public enum MatchStatus {
    PENDING(1),PLAYING(2),COMPLETED(3),CANCELLED(4),WALK_OVER(5);

    private int matchStatusCode;

    MatchStatus(int userTypeCode) {
        this.matchStatusCode = userTypeCode;
    }

    public MatchStatus getUserTypeByCode(int userTypeCode) {
        return Arrays.stream(values()).filter(value -> value.matchStatusCode == userTypeCode).findFirst().orElseThrow(() -> new SystemFailureException(SystemErrorCode.SOMETHING_WENT_WRONG));
    }

    private int getMatchStatusCode() {
        return this.matchStatusCode;
    }
}
