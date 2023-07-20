package com.titan.path.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FailedOperationException extends IllegalStateException {

    public FailedOperationException(String s) {
        super(s);
    }

}
