package com.pespurse.global.exception;

import com.pespurse.global.response.ResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SystemFailureException extends RuntimeException implements Serializable {

    private ResponseCode responseCode;
    public SystemFailureException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}
