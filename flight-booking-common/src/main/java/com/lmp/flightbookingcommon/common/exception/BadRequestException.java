package com.lmp.flightbookingcommon.common.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final String messageKey;
    private final transient Object[] args;

    public BadRequestException(String messageKey, Object... args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

}
