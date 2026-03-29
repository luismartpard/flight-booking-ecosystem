package com.lmp.flightbookingcommon.common.exception;

import java.util.Map;

public class ApplicationContractViolationException extends BadRequestException {


    public ApplicationContractViolationException(String messageKey) {
        super(messageKey);
    }

    public ApplicationContractViolationException(String messageKey, Object... args) {
        super(messageKey, args);
    }

    public ApplicationContractViolationException(String messageKey, Map<String, Object> params) {
        super(messageKey, new Object[]{}, params);
    }

}
