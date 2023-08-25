package com.example.Plowithme.exception.custom;

import com.example.Plowithme.exception.error.ErrorCode;
import org.aspectj.bridge.Message;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
