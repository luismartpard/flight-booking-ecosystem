package com.lmp.flightbookingcommon.common.exception;

public class MessageNotReadableException extends BadRequestException {

    public MessageNotReadableException(String messageKey, Object... args) {
        super(messageKey, args);
    }

}
