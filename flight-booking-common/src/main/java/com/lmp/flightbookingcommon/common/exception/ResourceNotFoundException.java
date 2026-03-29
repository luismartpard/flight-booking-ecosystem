package com.lmp.flightbookingcommon.common.exception;

public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException(String messageKey, Object... args) {
        super(messageKey, args);
    }

}
