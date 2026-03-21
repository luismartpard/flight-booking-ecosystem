package com.lmp.geographyservice.application.exception;

import com.lmp.flightbookingcommon.common.exception.BadRequestException;

import java.util.Objects;

public class InvalidCountryPageQueryException extends BadRequestException {

    private final Object[] args;

    public InvalidCountryPageQueryException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

}
