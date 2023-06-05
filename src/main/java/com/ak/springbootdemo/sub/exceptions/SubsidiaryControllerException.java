package com.ak.springbootdemo.sub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Subsidiary Controller Exception
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SubsidiaryControllerException  extends RuntimeException {
    public SubsidiaryControllerException(String message) {
        super(message);
    }

}
