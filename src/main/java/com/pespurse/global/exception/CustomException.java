package com.pespurse.global.exception;

import com.pespurse.global.response.ResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Component
public class CustomException extends RuntimeException{

    private ResponseCode responseCode;

    public CustomException(ResponseCode responseCode) {
        super(responseCode.message(), null, false, true);
        this.responseCode = responseCode;
    }
}
