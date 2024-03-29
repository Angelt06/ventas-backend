package com.ventasbackend.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
public class ApiRequestException extends RuntimeException {

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);

    }

}