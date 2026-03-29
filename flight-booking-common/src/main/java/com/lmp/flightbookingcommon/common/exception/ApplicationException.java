package com.lmp.flightbookingcommon.common.exception;

import java.util.Map;

public class ApplicationException extends RuntimeException {

    private final String messageKey;
    private final Object[] args;
    private final Map<String, Object> params;

    public ApplicationException(String messageKey, Object[] args, Map<String, Object> params) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
        this.params = params;
    }

    protected ApplicationException(String messageKey) {
        this(messageKey, new Object[]{}, Map.of());
    }

    protected ApplicationException(String messageKey, Object... args) {
        this(messageKey, args, Map.of());
    }

    public String getMessageKey() {
        return messageKey;
    }

    public Object[] getArgs() {
        return args;
    }

    public Map<String, Object> getParams() {
        return params;
    }

}
