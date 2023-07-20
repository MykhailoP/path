package com.titan.path.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IllegalArgumentNameException extends IllegalArgumentException {

    public IllegalArgumentNameException(String s) {
        super(s);
    }

}
