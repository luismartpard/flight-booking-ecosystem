package com.lmp.flightbookingcommon.infrastructure.web.exception;

import com.lmp.flightbookingcommon.common.exception.ApplicationException;
import com.lmp.flightbookingcommon.common.exception.BadRequestException;
import com.lmp.flightbookingcommon.common.exception.ResourceAlreadyExistsException;
import com.lmp.flightbookingcommon.common.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;

public class ExceptionStatusResolver {

    public HttpStatus resolve(ApplicationException ex) {

        if (ex instanceof BadRequestException) {
            return HttpStatus.BAD_REQUEST; //400
        }

        if(ex instanceof ResourceNotFoundException) {
            return HttpStatus.NOT_FOUND; // 404
        }

        if (ex instanceof ResourceAlreadyExistsException) {
            return HttpStatus.CONFLICT; // 409
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;

    }

}
