package com.pespurse.tournament.repo.entity.enums;

import com.pespurse.global.exception.SystemFailureException;
import com.pespurse.global.exception.errorCode.SystemErrorCode;

import java.util.Arrays;

public enum TournamentType {
    LEAGUE(1),KNOCKOUT_COMPETITION(2),SEASON(3),SEASON_RELEGATION(4),SUPER_LEAGUE(5),LEAGUE_PLUS_KNOCKOUT(6);

    private int tournamentTypeCode;

    TournamentType(int userTypeCode) {
        this.tournamentTypeCode = userTypeCode;
    }

    public static TournamentType getTournamentTypeByCode(int userTypeCode) {
        return Arrays.stream(values()).filter(value -> value.tournamentTypeCode == userTypeCode).findFirst().orElseThrow(() -> new SystemFailureException(SystemErrorCode.SOMETHING_WENT_WRONG));
    }

    public int getTournamentTypeCode() {
        return this.tournamentTypeCode;
    }
}
