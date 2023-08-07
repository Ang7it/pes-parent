package com.pespurse.players.repo.entity.enums;

import com.pespurse.global.exception.SystemFailureException;
import com.pespurse.global.exception.errorCode.SystemErrorCode;

import java.util.Arrays;

public enum CardType {

    STANDARD(1), LEGENDARY(2), EPIC(3), FEATURED(4), HIGHLIGHT(5), TRENDING(6);
    int cardTypeCode;

    CardType(int cardTypeCode){
        this.cardTypeCode = cardTypeCode;
    }

    CardType getCardTypeByCode(int cardTypeCode) {
        return Arrays.stream(values()).filter(value -> value.cardTypeCode == cardTypeCode).findFirst().orElseThrow(() -> new SystemFailureException(SystemErrorCode.SOMETHING_WENT_WRONG));
    }

    public int getCardTypeCode() {
        return this.cardTypeCode;
    }
}
