package com.pespurse.tournament.repo.entity.enums;

import com.pespurse.global.exception.SystemFailureException;
import com.pespurse.global.exception.errorCode.SystemErrorCode;

import java.util.Arrays;

public enum TournamentStatus {
    CREATED(1),ONGOING(2),COMPLETED(3),REGISTRATION_OPEN(4),REGISTRATION_CLOSED(5),CANCELLED(6);

    private int tournamentStatusCode;

    TournamentStatus(int userTypeCode) {
        this.tournamentStatusCode = userTypeCode;
    }

    public static TournamentStatus getTournamentStatusByCode(int userTypeCode) {
        return Arrays.stream(values()).filter(value -> value.tournamentStatusCode == userTypeCode).findFirst().orElseThrow(() -> new SystemFailureException(SystemErrorCode.SOMETHING_WENT_WRONG));
    }

    public int getTournamentStatusCode() {
        return this.tournamentStatusCode;
    }
}
