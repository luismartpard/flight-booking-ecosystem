package com.lmp.flightbookingcommon.common.exception;

import java.util.Map;

public class ResourceAlreadyExistsException extends ApplicationException {

    public ResourceAlreadyExistsException(String messageKey, Object[] args, Map<String, Object> params) {
        super(messageKey, args, params);
    }

    public ResourceAlreadyExistsException(String messageKey) {
        super(messageKey);
    }

    public ResourceAlreadyExistsException(String messageKey, Object... args) {
        super(messageKey, args);
    }
}
