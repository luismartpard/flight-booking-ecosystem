package com.lmp.flightbookingcommon.infrastructure.web.response;

import java.time.OffsetDateTime;

public record ErrorResponse(

        int status,
        String error,
        String messageKey,
        String message,
        String path,
        OffsetDateTime timestamp

) {

    public static ErrorResponse of(
            int status,
            String error,
            String messageKey,
            String message,
            String path) {
        return new ErrorResponse(status, error, messageKey, message, path, OffsetDateTime.now());
    }

}