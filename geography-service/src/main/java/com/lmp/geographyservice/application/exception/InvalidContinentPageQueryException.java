package com.lmp.geographyservice.application.exception;


import com.lmp.flightbookingcommon.common.exception.BadRequestException;

public class InvalidContinentPageQueryException extends BadRequestException {

    private final Object[] args;

    public InvalidContinentPageQueryException(String message, Object... args) {
        super(message,  args);
        this.args = args;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }
}
