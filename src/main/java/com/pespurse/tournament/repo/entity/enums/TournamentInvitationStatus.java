package com.pespurse.tournament.repo.entity.enums;

import com.pespurse.global.exception.SystemFailureException;
import com.pespurse.global.exception.errorCode.SystemErrorCode;

import java.util.Arrays;

public enum TournamentInvitationStatus {
    CREATED(1),SENT(2),ACCEPTED(3),DECLINED(4),CANCELLED(5);

    private int tournamentInvitationStatusCode;

    TournamentInvitationStatus(int userTypeCode) {
        this.tournamentInvitationStatusCode = userTypeCode;
    }

    public TournamentInvitationStatus getTournamentInvitationStatusByCode(int userTypeCode) {
        return Arrays.stream(values()).filter(value -> value.tournamentInvitationStatusCode == userTypeCode).findFirst().orElseThrow(() -> new SystemFailureException(SystemErrorCode.SOMETHING_WENT_WRONG));
    }

    public int getTournamentInvitationStatusCode() {
        return this.tournamentInvitationStatusCode;
    }
}
