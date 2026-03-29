package com.lmp.flightbookingcommon.common.exception;

import java.util.Map;

public class BadRequestException extends ApplicationException {

    public BadRequestException(String messageKey) {
        super(messageKey);
    }

    public BadRequestException(String messageKey, Object... args) {
        super(messageKey, args);
    }

    public BadRequestException(String messageKey, Object[] args, Map<String, Object> params) {
        super(messageKey, args, params);
    }

}
