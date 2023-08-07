package com.pespurse.players.repo.entity.enums;

import com.pespurse.global.exception.SystemFailureException;
import com.pespurse.global.exception.errorCode.SystemErrorCode;

import java.util.Arrays;

public enum PlayingStyle {
    GOAL_POACHER(1),DUMMY_RUNNER(2), FOX_IN_THE_BOX(3), TARGET_MAN(4), DEEP_LYING_FORWARD(5), CREATIVE_PLAYMAKER(6), PROLIFIC_WINGER(7),
    ROAMING_FLANK(8), CROSS_SPECIALIST(9), CLASSIC_NO_10(10), HOLE_PLAYER(11), BOX_TO_BOX(12), ANCHOR_MAN(13), THE_DESTROYER(15), ORCHESTRATOR(16), BUILD_UP(17), EXTRA_FRONTMAN(18),
    OFFENSIVE_FULL_BACK(19), DEFENSIVE_FULL_BACK(20), FULL_BACK_FINIHSHER(21), OFFENSIVE_GOALKEEPER(22), DEFENSIVE_GOALKEEPER(23);
    private int playingStyleCode;

    PlayingStyle(int playingStyleCode) {
        this.playingStyleCode = playingStyleCode;
    }

    PlayingStyle getPlayingStyleByPlayingCode(int playingStyleCode) {
        return Arrays.stream(values()).filter(value -> value.playingStyleCode == playingStyleCode).findFirst().orElseThrow(() -> new SystemFailureException(SystemErrorCode.SOMETHING_WENT_WRONG));
    }

    public int getPlayingStyleCode() {
        return this.playingStyleCode;
    }
}
