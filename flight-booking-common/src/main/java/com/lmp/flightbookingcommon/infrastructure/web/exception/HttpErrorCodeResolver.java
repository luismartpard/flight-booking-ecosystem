package com.lmp.flightbookingcommon.infrastructure.web.exception;

import org.springframework.http.HttpStatus;

public class HttpErrorCodeResolver {

    public String resolve(HttpStatus  status) {

        return switch (status) {
            case BAD_REQUEST -> "request.invalid";
            case NOT_FOUND -> "resource.not_found";
            case CONFLICT -> "resource.already_exists";
            default -> "internal.error";
        };

    }

}
